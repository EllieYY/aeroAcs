package com.wim.aero.acs.service;

//import io.netty.example.study.common.OperationResult;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.db.entity.TaskDetail;
import com.wim.aero.acs.db.service.impl.TaskDetailServiceImpl;
import com.wim.aero.acs.model.TaskCommandState;
import com.wim.aero.acs.model.command.CommandInfo;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.command.ScpCmdResponse;
import com.wim.aero.acs.model.mq.ScpSeqMessage;
import com.wim.aero.acs.model.request.TaskRequest;
import com.wim.aero.acs.model.scp.ScpSeq;
import com.wim.aero.acs.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class RequestPendingCenter {
    /** streamId和Command信息Map     */
    static private Map<String, CommandInfo> commandInfoMap = new ConcurrentHashMap<>();
    /** streamId和seqId的Map        */
    static private Map<String, ScpSeq> streamSeqMap = new ConcurrentHashMap<>();

    private final TaskDetailServiceImpl taskDetailService;
    private final RestUtil restUtil;
    @Autowired
    public RequestPendingCenter(TaskDetailServiceImpl taskDetailService, RestUtil restUtil) {
        this.taskDetailService = taskDetailService;
        this.restUtil = restUtil;
    }

    /**
     * 向设备发送单条命令
     * @param request
     * @param cmd
     * @return
     */
    public int sendCmd(TaskRequest request, ScpCmd cmd) {
        // 向设备发送
        add(request.getTaskId(), request.getTaskName(), request.getTaskSource(), Arrays.asList(cmd));
        ScpCmdResponse response = restUtil.sendSingleCmd(cmd);
        updateSeq(Arrays.asList(response));
        return response.getCode();
    }

    /**
     * 向设备发送多条命令
     * @param request
     * @param cmdList
     */
    public int sendCmdList(TaskRequest request, List<ScpCmd> cmdList) {
        if (cmdList.size() <= 0) {
            return 0;
        }

        this.add(request.getTaskId(), request.getTaskName(), request.getTaskSource(), cmdList);
        List<ScpCmdResponse> responseList = restUtil.sendMultiCmd(cmdList);
        int sum = responseList.stream().mapToInt(response -> (response.getCode() == 0 ? 1 : 0)).sum();
        if (sum == 0) {
            return -1;
        }
        this.updateSeq(responseList);
        return 0;
    }


    /** 命令集合添加 */
    private void add(long taskId, String taskName, int taskSource, List<ScpCmd> commandInfoList) {
        log.info("[下发命令条数] {}", commandInfoList.size());

        List<TaskDetail> taskDetailList = new ArrayList<>();
        for (ScpCmd cmd:commandInfoList) {
            CommandInfo commandInfo = new CommandInfo(
                    taskId, taskName, taskSource, cmd.getStreamId(), cmd.getScpId(), cmd.getCommand(), 0);

            // 更新集合
            commandInfoMap.put(cmd.getStreamId(), commandInfo);

            Date curTime = commandInfo.getCmdDate();
            String state = (taskId == Constants.CONNECT_TASK_ID) ?
                    TaskCommandState.SUCCESS.value() : TaskCommandState.INIT.value();
            TaskDetail taskDetail = new TaskDetail(
                    taskId, taskName, taskSource, cmd.getCommand(),
                    curTime, null, DateUtil.dateAddMins(curTime,5),
                    state,
                    cmd.getStreamId(),
                    cmd.toString()
            );

            taskDetail.setCardNo(cmd.getCardNo());
            taskDetail.setScpId(Integer.parseInt(cmd.getScpId()));

            taskDetailList.add(taskDetail);
        }

        taskDetailService.saveBatch(taskDetailList);
    }

    /** 更新seqNo */
    private List<CommandInfo> updateSeq(List<ScpCmdResponse> cmdResponseList) {
        log.info("[通信服务响应命令条数] - {}", cmdResponseList.size());
        log.info(cmdResponseList.toString());

        List<TaskDetail> taskDetailList = new ArrayList<>();
        List<CommandInfo> result = new ArrayList<>();
        for (ScpCmdResponse response:cmdResponseList) {
            String streamId = response.getStreamId();
            int scpId = response.getScpId();
            long seqNo = Long.parseLong(response.getSequenceNumber());
            int code = response.getCode();

            // 更新seqNo
            if (commandInfoMap.containsKey(streamId)) {
                CommandInfo commandInfo = commandInfoMap.get(streamId);
                commandInfo.setSeqId(seqNo);
                commandInfo.setCommCode(code);
                commandInfoMap.put(streamId, commandInfo);

                String state = TaskCommandState.INIT.value();
                if (code == Constants.REST_CODE_SUCCESS) {
                    streamSeqMap.put(streamId, new ScpSeq(scpId, seqNo));
                    state = TaskCommandState.DOING.value();
                } else {
                    state = TaskCommandState.FAIL.value();
                    result.add(commandInfo);
                }

                Date curTime = commandInfo.getCmdDate();
                taskDetailList.add(new TaskDetail(
                        commandInfo.getTaskId(), commandInfo.getTaskName(), commandInfo.getTaskSource(),
                        commandInfo.getCommand(),
                        curTime, new Date(), DateUtil.dateAddMins(curTime,5),
                        state,
                        commandInfo.getStreamId(),
                        response.toString()
                ));

            } else {
                log.error("[{} - 通信服务错误] 返回未定义streamId : {}", scpId, streamId);
            }
        }

        log.info("[stream:seq] {}", streamSeqMap.toString());
//        taskDetailService.updateTaskStateBatch(taskDetailList);

        log.info("[发送失败命令条数] {}", result.size());
        return result;
    }

    /** 控制器返回命令生效结果 */
    public boolean commandResponse(ScpSeqMessage scpSeqMessage) {
        int scpId = scpSeqMessage.getScpId();
        long seqNo = scpSeqMessage.getSeq();
        int code = scpSeqMessage.getStatus();
        int reason = scpSeqMessage.getReason();
        String detail = scpSeqMessage.getDetail();

//        log.info("seq:{}, code:{}", seqNo, code);

        List<String> streamList = getStreamIdsBySeqId(scpId, seqNo);
        if (streamList.size() <= 0) {
            return false;
        }

        List<TaskDetail> taskDetailList = new ArrayList<>();
        for (String key:streamList) {
            CommandInfo commandInfo = commandInfoMap.get(key);
            commandInfo.setReason(reason);
            commandInfo.setCommandStatus(code);

            String state = TaskCommandState.FAIL.value();
            if (code != Constants.CMND_OK) {
                log.info("[{} 失败指令] seqNo[{}], reason[{}], cmd[{}]", scpId, seqNo, reason, commandInfo.getCommand());
            } else {
                state = TaskCommandState.SUCCESS.value();
//                log.info("[指令结果] seqNo[{}], code[{}], cmd[{}]", seqNo, code, commandInfo.getCommand());
            }

            Date curTime = commandInfo.getCmdDate();
            taskDetailList.add(new TaskDetail(
                    commandInfo.getTaskId(), commandInfo.getTaskName(), commandInfo.getTaskSource(),
                    commandInfo.getCommand(),
                    curTime, scpSeqMessage.getCmdDate(), DateUtil.dateAddMins(curTime,5),
                    state,
                    commandInfo.getStreamId(),
                    detail
            ));

            // 移除命令集合
            removeStreamId(key);
        }

        taskDetailService.updateTaskStateBatch(taskDetailList);

        return true;
    }


    /** 通过seqNo查找streamId列表 */
    public List<String> getStreamIdsBySeqId(int scpId, long seqId) {
        List<String> streamIdList = new ArrayList<>();
        for(Map.Entry<String, ScpSeq> entry : streamSeqMap.entrySet()){
            String streamId = entry.getKey();
            ScpSeq scpSeq = entry.getValue();
            if (scpSeq.getScpId() == scpId && scpSeq.getSeq() == seqId) {
                streamIdList.add(streamId);
            }
        }
        return streamIdList;
    }

    private void removeStreamId(String streamId) {
        if (streamSeqMap.containsKey(streamId)) {
            streamSeqMap.remove(streamId);
        }

        if (commandInfoMap.containsKey(streamId)) {
            commandInfoMap.remove(streamId);
        }
    }


}
