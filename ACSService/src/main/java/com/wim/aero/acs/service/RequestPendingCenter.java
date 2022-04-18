package com.wim.aero.acs.service;

//import io.netty.example.study.common.OperationResult;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.command.CmdDownloadInfo;
import com.wim.aero.acs.model.command.CommandInfo;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.command.ScpCmdResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /** 命令集合添加 */
    public static void add(long taskId, List<ScpCmd> commandInfoList) {
        for (ScpCmd cmd:commandInfoList) {
            CommandInfo commandInfo = new CommandInfo(
                    taskId, cmd.getStreamId(), cmd.getScpId(), cmd.getCommand(), 0);
            //TODO：入库

            // 更新集合
            commandInfoMap.put(cmd.getStreamId(), commandInfo);
//            streamSeqMap.put(cmd.getStreamId(), cmd.getSeqId());
        }
    }

    /** 更新seqNo */
    public static List<CommandInfo> updateSeq(List<ScpCmdResponse> cmdResponseList) {
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

                if (code == 0) {
                    streamSeqMap.put(streamId, seqNo);
                } else {
                    result.add(commandInfo);
                }
            }
        }

//        log.info(streamSeqMap.toString());
//        log.info(commandInfoMap.toString());

        return result;
    }

    /** 控制器返回命令生效结果 */
    public static void commandResponse(long seqNo, int code, int reason) {
        List<String> streamList = getStreamIdsBySeqId(seqNo);
        for (String key:streamList) {
            CommandInfo commandInfo = commandInfoMap.get(key);
            commandInfo.setReason(reason);
            commandInfo.setCommandStatus(code);

            // TODO:存库

            log.info("[指令结果] seqNo[{}], code[{}], cmd[{}]", seqNo, code, commandInfo.getCommand());
//            if (code != Constants.CMND_OK) {
//                log.info("[失败指令] seqNo[{}], code[{}], cmd[{}]", seqNo, code, commandInfo.getCommand());
//            }

            // 移除命令集合
//            removeStreamId(key);
        }
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
