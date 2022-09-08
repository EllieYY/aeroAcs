package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.AlarmMessage;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.model.mq.StatusMessage;
import com.wim.aero.acs.service.QueueProducer;
import com.wim.aero.acs.service.ScpMessageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: SCPReplySrCp
 * @author: Ellie
 * @date: 2022/04/13 11:34
 * @description:
 **/
@Data
@Slf4j
public class SCPReplySrCp extends ReplyBody {
    private int first;				// number of the first Control Point
    private int count;				// number of CP status entries
    private List<Integer> status;			// CP status (trl07 encoded)

    @Override
    public void process(QueueProducer queueProducer, int scpId) {

        // TODO： 状态解析
        Map<Integer, Integer> cosStateCodeMap = new HashMap<>();
        cosStateCodeMap.put(0x00, Constants.TRAGET_STATE_CLOSE);
        cosStateCodeMap.put(0x01, Constants.TRAGET_STATE_OPEN);
        cosStateCodeMap.put(0x02, Constants.TRAGET_STATE_FAULT);
        cosStateCodeMap.put(0x03, Constants.TRAGET_STATE_FAULT);
        cosStateCodeMap.put(0x04, Constants.TRAGET_STATE_OPEN);
        cosStateCodeMap.put(0x05, Constants.TRAGET_STATE_OPEN);
        cosStateCodeMap.put(0x06, Constants.TRAGET_STATE_FAULT);
        cosStateCodeMap.put(0x07, Constants.TRAGET_STATE_FAULT);
        cosStateCodeMap.put(0x08, Constants.TRAGET_STATE_INVALID);
        cosStateCodeMap.put(0x10, Constants.TRAGET_STATE_CLOSE);
        cosStateCodeMap.put(0x20, Constants.TRAGET_STATE_OPEN);
        cosStateCodeMap.put(0x40, Constants.TRAGET_STATE_OPEN);
        cosStateCodeMap.put(0x80, Constants.TRAGET_STATE_CLOSE);

        if ( count == 1 && status.size() > 0) {
            int cosState = status.get(0);
            int deviceStatus = cosStateCodeMap.get(cosState);

            StatusMessage sMessage = new StatusMessage(
                    -1, System.currentTimeMillis(), scpId,
                    Constants.tranSrcCP, first, 0x08, 0, deviceStatus,
                    Constants.TRAN_TABLE_SRC_CP,
                    this.toString());
            queueProducer.sendStatusMessage(sMessage);
        } else {
//        // 逻辑编号未必是连续的
            String info = "FirstCp:" + first + ", ";
            for (int i = 0; i < count; i++) {
                info += (first + i) + ":" + status.get(i);
            }
            log.error("非法控制点状态上报：{}", info);
        }

    }
}
