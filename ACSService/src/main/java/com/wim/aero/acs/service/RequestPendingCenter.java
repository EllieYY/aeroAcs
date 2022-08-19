package com.wim.aero.acs.service;

//import io.netty.example.study.common.OperationResult;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.db.entity.DEmployeeAuth;
import com.wim.aero.acs.db.entity.TaskDetail;
import com.wim.aero.acs.db.service.impl.DEmployeeAuthServiceImpl;
import com.wim.aero.acs.db.service.impl.TaskDetailServiceImpl;
import com.wim.aero.acs.model.TaskCommandState;
import com.wim.aero.acs.model.command.CommandInfo;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.command.ScpCmdResponse;
import com.wim.aero.acs.model.mq.ScpSeqMessage;
import com.wim.aero.acs.model.request.TaskRequest;
import com.wim.aero.acs.model.scp.ScpSeq;
import com.wim.aero.acs.util.DateUtil;
import com.wim.aero.acs.util.StringUtil;
import com.wim.aero.acs.util.cache.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RequestPendingCenter implements CacheManagerAware {
//    /** streamId和Command信息Map     */
//    static private Map<String, CommandInfo> commandInfoMap = new ConcurrentHashMap<>();
//    /** streamId和seqId的Map        */
//    static private Map<String, ScpSeq> streamSeqMap = new ConcurrentHashMap<>();

    private final TaskDetailServiceImpl taskDetailService;
    private final RestUtil restUtil;
    private final ExpireCacheManager expireCacheManager;
    private final DEmployeeAuthServiceImpl employeeAuthService;
    @Autowired
    public RequestPendingCenter(TaskDetailServiceImpl taskDetailService,
                                RestUtil restUtil,
                                ExpireCacheManager expireCacheManager,
                                DEmployeeAuthServiceImpl employeeAuthService) {
        this.taskDetailService = taskDetailService;
        this.restUtil = restUtil;
        this.expireCacheManager = expireCacheManager;
        this.employeeAuthService = employeeAuthService;
        setCacheManager(expireCacheManager);
        expireCacheManager.startManager();
    }

    private Cache<String, ScpSeq> mapCache;
    private Cache<String, CommandInfo> cmdCache;
    private Cache<String, ScpSeqMessage> seqCache;

    @Override
    public void setCacheManager(CacheManager cacheManager) {
        mapCache = cacheManager.getCache("streamSeq");
        cmdCache = cacheManager.getCache("command");
        seqCache = cacheManager.getCache("seqCache");
    }


    /**
     * 向设备发送单条命令
     * @param request
     * @param cmd
     * @return
     */
    public int sendCmd(TaskRequest request, ScpCmd cmd) {
        // 向设备发送

        ScpCmdResponse response = restUtil.sendSingleCmd(cmd);


        Map<String, ScpCmdResponse>  responseMap =
                Arrays.asList(response).stream().collect(Collectors.toMap(ScpCmdResponse::getStreamId, item -> item));

        // 响应处理
        log.info(response.toString());
        commandCollected(request, Arrays.asList(cmd), responseMap);

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

        // 控制单次发送条数
        List<List<ScpCmd>> batchCardList = StringUtil.fixedGrouping(cmdList, Constants.BATCH_CMD_COUNT);
        for (List<ScpCmd> batchCmd:batchCardList) {

            List<ScpCmdResponse> responseList = restUtil.sendMultiCmd(batchCmd);
            Map<String, ScpCmdResponse>  responseMap =
                    responseList.stream().collect(Collectors.toMap(ScpCmdResponse::getStreamId, item -> item));

            // 响应处理
            log.info("[通信服务响应命令条数] - {}", responseList.size());
            log.info(responseList.toString());
            commandCollected(request, batchCmd, responseMap);

            int sum = responseList.stream().mapToInt(response -> (response.getCode() == 0 ? 1 : 0)).sum();
            if (sum == 0) {
                return -1;
            }
        }

        return 0;
    }

    /**
     * 对同步返回结果先做失败匹配
     * @param request
     * @param batchCmd
     * @param responseMap
     */
    private void commandCollected(TaskRequest request, List<ScpCmd> batchCmd, Map<String, ScpCmdResponse> responseMap) {
        long taskId = request.getTaskId();
        String taskName = request.getTaskName();
        int taskSource = request.getTaskSource();

        int failSum = 0;
        for (ScpCmd cmd:batchCmd) {
            String uid = cmd.getStreamId();
            String commandStatus = TaskCommandState.FAIL.value();
            Date cmdTime = new Date();
            String detail = cmd.toString();
            int cmdType = cmd.getType();

            // 返回结果匹配
            ScpCmdResponse cmdResponse = responseMap.get(uid);
            if (cmdResponse != null) {
                int scpId = cmdResponse.getScpId();
                long seqNo = Long.parseLong(cmdResponse.getSequenceNumber());

                int code = cmdResponse.getCode();
                if (code != Constants.REST_CODE_SUCCESS) {   // 下发失败的：修改status、detail，不进缓存，存对应关系
                    failSum++;
                    commandStatus = TaskCommandState.FAIL.value();
                    detail = cmdResponse.toString();

                    // 授权汇总表更新
                    if (cmdType == Constants.SCP_CMD_CARD_ADD || cmdType == Constants.SCP_CMD_CARD_DEL) {
                        saveOneEmployeeAuth(scpId, cmd.getCardNo(), cmd.getAlvlListStr(),
                                cmdTime, commandStatus, detail, cmd.getCommand());
                    }

                } else {   // 下发成功的：修改status初始状态，进缓存
                    // 命令信息组装
                    String scpIdStr = cmd.getScpId();
                    commandStatus = TaskCommandState.INIT.value();
                    CommandInfo commandInfo = new CommandInfo(
                            taskId, taskName, taskSource,
                            uid, scpIdStr, cmd.getCommand(), code);
                    commandInfo.setType(cmd.getType());
                    commandInfo.setSeqId(seqNo);
                    if (cmd.getType() == Constants.SCP_CMD_CARD_ADD || cmd.getType() == Constants.SCP_CMD_CARD_DEL) {
                        commandInfo.setCardNo(cmd.getCardNo());
                        commandInfo.setAlvlListStr(cmd.getAlvlListStr());
                    }

                    // 做匹配进入缓存
                    String seqKey = getSeqKey(scpId, seqNo);
                    ScpSeqMessage seqMessage = seqCache.get(seqKey);
                    if (seqMessage == null) {    // 双向匹配预留：匹配失败
                        cmdCache.put(uid, commandInfo);
                        mapCache.put(uid, new ScpSeq(scpId, seqNo));
                    } else {                     // 匹配成功
                        commandStatus = TaskCommandState.SUCCESS.value();
                        detail = seqMessage.getDetail();
                    }

                    // 存入数据库
                    commandStatus = (taskId == Constants.CONNECT_TASK_ID) ?
                            TaskCommandState.SUCCESS.value() : commandStatus;
                }
            }

            TaskDetail taskDetail = new TaskDetail(
                    taskId, taskName, taskSource, cmd.getCommand(),
                    cmdTime, cmdTime, DateUtil.dateAddMins(cmdTime,5),
                    commandStatus,
                    cmd.getStreamId(),
                    detail
            );

            taskDetail.setCardNo(cmd.getCardNo());
            taskDetail.setScpId(Integer.parseInt(cmd.getScpId()));

            taskDetailService.save(taskDetail);
        }
        log.info("[发送失败命令条数] {}", failSum);

    }


    /** 命令集合添加 */
    private void add(long taskId, String taskName, int taskSource, List<ScpCmd> commandInfoList) {
        log.info("[下发命令条数] {}", commandInfoList.size());

        List<TaskDetail> taskDetailList = new ArrayList<>();
        for (ScpCmd cmd:commandInfoList) {
            CommandInfo commandInfo = new CommandInfo(
                    taskId, taskName, taskSource, cmd.getStreamId(), cmd.getScpId(), cmd.getCommand(), 0);

            commandInfo.setType(cmd.getType());
            // 卡片删除和增加
            if (cmd.getType() == Constants.SCP_CMD_CARD_ADD || cmd.getType() == Constants.SCP_CMD_CARD_DEL) {
                commandInfo.setCardNo(cmd.getCardNo());
                commandInfo.setAlvlListStr(cmd.getAlvlListStr());
            }

            // 更新集合
//            log.info("add cmdCache:{}", cmd.getStreamId());
            cmdCache.put(cmd.getStreamId(), commandInfo);

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

            taskDetailService.save(taskDetail);
//            taskDetailList.add(taskDetail);
        }

//        taskDetailService.saveBatch(taskDetailList);
    }


    /** 更新seqNo */
    public List<CommandInfo> updateSeq(List<ScpCmdResponse> cmdResponseList) {
        log.info("[通信服务响应命令条数] - {}", cmdResponseList.size());
        log.info(cmdResponseList.toString());

        List<TaskDetail> taskDetailList = new ArrayList<>();
        List<CommandInfo> result = new ArrayList<>();
        for (ScpCmdResponse response:cmdResponseList) {
            String streamId = response.getStreamId();
            int scpId = response.getScpId();
            long seqNo = Long.parseLong(response.getSequenceNumber());
            int code = response.getCode();

            if (cmdCache.containsKey(streamId)) {
                CommandInfo commandInfo = (CommandInfo) cmdCache.get(streamId);
                if (commandInfo == null) {
                    continue;
                }
                commandInfo.setSeqId(seqNo);
                commandInfo.setCommCode(code);

                String state = TaskCommandState.INIT.value();
                if (code != Constants.REST_CODE_SUCCESS) {   // 明确下发失败的
                    cmdCache.remove(streamId);
                    state = TaskCommandState.FAIL.value();
                    result.add(commandInfo);

                    // 单条更新
                    taskDetailService.updateTaskState(state, response.toString(), new Date(), commandInfo.getStreamId());
                } else {
                    // 做匹配
                    String seqKey = getSeqKey(scpId, seqNo);
                    ScpSeqMessage seqMessage = seqCache.get(seqKey);
                    if (seqMessage == null) {    // 匹配失败
                        cmdCache.put(streamId, commandInfo);
                        mapCache.put(streamId, new ScpSeq(scpId, seqNo));
                    } else {                     // 匹配成功
                        cmdCache.remove(streamId);
                        state = TaskCommandState.SUCCESS.value();

                        // 单条更新
                        taskDetailService.updateTaskState(state,
                                seqMessage.getDetail(),
                                commandInfo.getCmdDate(),
                                commandInfo.getStreamId());
                    }
                }
            } else {
                log.error("[{} - 通信服务错误] 返回未定义streamId : {}", scpId, streamId);
            }
        }

//        printMap(mapCache);

        log.info("[发送失败命令条数] {}", result.size());
        return result;
    }


    private void printMap(Cache map) {
        for (Object key:map.keySet()) {
            log.info("{}:{}", key, map.get(key));
        }
    }


    private String getSeqKey(int scpId, long seqNo) {
        return scpId + ":" + seqNo;
    }

    /** 控制器返回命令生效结果 */
    public boolean commandResponse(ScpSeqMessage scpSeqMessage) {
        int scpId = scpSeqMessage.getScpId();
        long seqNo = scpSeqMessage.getSeq();

//        // TODO:入缓存
//        String seqKey = getSeqKey(scpId, seqNo);
//        seqCache.put(seqKey, scpSeqMessage, Constants.SEQ_EXPIRE_SEC);

        int code = scpSeqMessage.getStatus();
        int reason = scpSeqMessage.getReason();
        String detail = scpSeqMessage.getDetail();

        // 获取seq对应的UID
        List<String> streamList = getStreamIdsBySeqId(scpId, seqNo);
        if (streamList.size() <= 0) {
            return false;
        }

//        log.info("[消息匹配] seq:{}, code:{}, streamId:{}", seqNo, code, streamList.toString());

        List<TaskDetail> taskDetailList = new ArrayList<>();
        for (String key:streamList) {
            String state = TaskCommandState.FAIL.value();

            CommandInfo commandInfo = cmdCache.get(key);
            if (commandInfo == null) {
                log.info("streamId超时：{}", key);
                // 改成单条更新
                taskDetailService.updateTaskState(state, "超时", scpSeqMessage.getCmdDate(), key);
                continue;
            } else {
                commandInfo.setReason(reason);
                commandInfo.setCommandStatus(code);

                if (code != Constants.CMND_OK) {
                    log.info("[{} 失败指令] seqNo[{}], reason[{}], cmd[{}]", scpId, seqNo, reason, commandInfo.getCommand());
                } else {
                    state = TaskCommandState.SUCCESS.value();
//                log.info("[指令结果] seqNo[{}], code[{}], cmd[{}]", seqNo, code, commandInfo.getCommand());
                }

                // 移除命令集合
                removeStreamId(key);
            }

            // 改成单条更新
            taskDetailService.updateTaskState(state, detail, scpSeqMessage.getCmdDate(), commandInfo.getStreamId());

            // 授权表写入
            if (commandInfo.getType() == Constants.SCP_CMD_CARD_ADD ||
                    commandInfo.getType() == Constants.SCP_CMD_CARD_DEL) {
                saveOneEmployeeAuth(scpId, commandInfo.getCardNo(), commandInfo.getAlvlListStr(),
                        scpSeqMessage.getCmdDate(), state, detail, commandInfo.getCommand());

            }

        }

        return true;
    }

    /**
     * 授权表状态更新
     * @return
     */
    private boolean saveOneEmployeeAuth(int scpId, String cardNo, String alvlStr, Date cmdDate,
                                        String state, String detail, String message) {
        DEmployeeAuth employeeAuth = employeeAuthService.getOneByScpIdAndCard(scpId, cardNo);
        if (employeeAuth == null) {
            employeeAuth = new DEmployeeAuth();
        }

//                log.info("查找授权表记录：{}", employeeAuth.toString());

        employeeAuth.setCardNo(cardNo);
        employeeAuth.setControllerId(scpId);
        employeeAuth.setAccessLevelIdList(alvlStr);
        employeeAuth.setStartTime(cmdDate);
        employeeAuth.setStatus(state);
        employeeAuth.setDetail(detail);
        employeeAuth.setMessage(message);

        boolean saveOk = employeeAuthService.saveOrUpdate(employeeAuth);
//        log.info("授权表保存：{} - {}", saveOk, employeeAuth.toString());
        return saveOk;
    }


    /** 通过seqNo查找streamId列表 */
    public List<String> getStreamIdsBySeqId(int scpId, long seqId) {
        List<String> streamIdList = new ArrayList<>();
        for (String key : mapCache.keySet()) {
            ScpSeq scpSeq = mapCache.get(key);
            if (scpSeq == null) {
                continue;
            }

            if (scpSeq.getScpId() == scpId && scpSeq.getSeq() == seqId) {
                streamIdList.add(key);
            }
        }

//        log.info("seq匹配：{}", streamIdList.toString());
        return streamIdList;
    }

    private void removeStreamId(String streamId) {
        if (mapCache.containsKey(streamId)) {
            mapCache.remove(streamId);
//            log.info("map移除 {}", streamId);
        }

        if (cmdCache.containsKey(streamId)) {
            cmdCache.remove(streamId);
//            log.info("cmd移除 {}", streamId);
        }
    }

}
