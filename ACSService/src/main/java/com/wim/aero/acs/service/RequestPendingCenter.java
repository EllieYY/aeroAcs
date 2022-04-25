package com.wim.aero.acs.service;

//import io.netty.example.study.common.OperationResult;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.db.entity.TaskDetail;
import com.wim.aero.acs.db.service.impl.TaskDetailServiceImpl;
import com.wim.aero.acs.model.TaskCommandState;
import com.wim.aero.acs.model.command.CommandInfo;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.command.ScpCmdResponse;
import com.wim.aero.acs.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class RequestPendingCenter {
    /** streamId和Command信息Map     */
    static private Map<String, CommandInfo> commandInfoMap = new ConcurrentHashMap<>();
    /** streamId和seqId的Map        */
    static private Map<String, Long> streamSeqMap = new ConcurrentHashMap<>();

//    private final TaskDetailServiceImpl taskDetailService;
//    @Autowired
//    public RequestPendingCenter(TaskDetailServiceImpl taskDetailService) {
//        this.taskDetailService = taskDetailService;
//    }

    @Autowired
    TaskDetailServiceImpl taskDetailService;
    @Autowired
    static TaskDetailServiceImpl conTaskDetailService;

    @PostConstruct
    public void init() {
        conTaskDetailService = taskDetailService;
    }

    /** 命令集合添加 */
    public void add(long taskId, String taskName, int taskSource, List<ScpCmd> commandInfoList) {
        log.info("[下发命令条数] - {}", commandInfoList.size());

        List<TaskDetail> taskDetailList = new ArrayList<>();
        for (ScpCmd cmd:commandInfoList) {
            CommandInfo commandInfo = new CommandInfo(
                    taskId, taskName, taskSource, cmd.getStreamId(), cmd.getScpId(), cmd.getCommand(), 0);

            // 更新集合
            commandInfoMap.put(cmd.getStreamId(), commandInfo);
//            streamSeqMap.put(cmd.getStreamId(), cmd.getSeqId());

            Date curTime = commandInfo.getCmdDate();
            taskDetailList.add(new TaskDetail(
                    taskId, taskName, taskSource, cmd.getCommand(),
                    curTime, null, DateUtil.dateAddMins(curTime,5),
                    TaskCommandState.INIT.value(),
                    cmd.getStreamId()
            ));
        }

        conTaskDetailService.saveBatch(taskDetailList);
    }

    /** 更新seqNo */
    public List<CommandInfo> updateSeq(List<ScpCmdResponse> cmdResponseList) {
        log.info("[通信服务响应命令条数] - {}", cmdResponseList.size());

        List<TaskDetail> taskDetailList = new ArrayList<>();
        List<CommandInfo> result = new ArrayList<>();
        for (ScpCmdResponse response:cmdResponseList) {
            String streamId = response.getStreamId();
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
                    streamSeqMap.put(streamId, seqNo);
                    state = TaskCommandState.DOING.value();
                } else {
                    result.add(commandInfo);
                }

                Date curTime = commandInfo.getCmdDate();
                taskDetailList.add(new TaskDetail(
                        commandInfo.getTaskId(), commandInfo.getTaskName(), commandInfo.getTaskSource(),
                        commandInfo.getCommand(),
                        curTime, new Date(), DateUtil.dateAddMins(curTime,5),
                        state,
                        commandInfo.getStreamId()
                ));

            } else {
                log.error("[通信服务错误] - 返回未定义streamId : {}", streamId);
            }

        }

        conTaskDetailService.updateTaskStateBatch(taskDetailList);

        return result;
    }

    /** 控制器返回命令生效结果 */
    public static void commandResponse(long seqNo, int code, int reason) {
        List<TaskDetail> taskDetailList = new ArrayList<>();
        List<String> streamList = getStreamIdsBySeqId(seqNo);
        for (String key:streamList) {
            CommandInfo commandInfo = commandInfoMap.get(key);
            commandInfo.setReason(reason);
            commandInfo.setCommandStatus(code);

            String state = TaskCommandState.FAIL.value();
            if (code != Constants.CMND_OK) {
                log.info("[失败指令] seqNo[{}], reason[{}], cmd[{}]", seqNo, reason, commandInfo.getCommand());
            } else {
                state = TaskCommandState.SUCCESS.value();
//                log.info("[指令结果] seqNo[{}], code[{}], cmd[{}]", seqNo, code, commandInfo.getCommand());
            }

            Date curTime = commandInfo.getCmdDate();
            taskDetailList.add(new TaskDetail(
                    commandInfo.getTaskId(), commandInfo.getTaskName(), commandInfo.getTaskSource(),
                    commandInfo.getCommand(),
                    curTime, new Date(), DateUtil.dateAddMins(curTime,5),
                    state,
                    commandInfo.getStreamId()
            ));

            // 移除命令集合
            removeStreamId(key);
        }

        // TODO:
        conTaskDetailService.updateTaskStateBatch(taskDetailList);
    }


    /** 通过seqNo查找streamId列表 */
    private static List<String> getStreamIdsBySeqId(long seqId) {
        List<String> streamIdList = new ArrayList<>();
        for(Map.Entry<String, Long> entry : streamSeqMap.entrySet()){
            String streamId = entry.getKey();
            long seqNo = entry.getValue();
            if (seqNo == seqId) {
                streamIdList.add(streamId);
            }
        }
        return streamIdList;
    }

    private static void removeStreamId(String streamId) {
        if (streamSeqMap.containsKey(streamId)) {
            streamSeqMap.remove(streamId);
        }

        if (commandInfoMap.containsKey(streamId)) {
            commandInfoMap.remove(streamId);
        }
    }

//
//    public void set(Long streamId, OperationResult operationResult) {
//        OperationResultFuture operationResultFuture = this.map.get(streamId);
//        if (operationResultFuture != null) {
//            operationResultFuture.setSuccess(operationResult);
//            this.map.remove(streamId);
//        }
//    }


}
