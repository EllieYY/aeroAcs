package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.AlarmMessage;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.model.scp.transaction.AlarmEvent;
import com.wim.aero.acs.service.QueueProducer;
import com.wim.aero.acs.service.ScpMessageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @title: SCPReplySrMp
 * @author: Ellie
 * @date: 2022/04/13 11:33
 * @description:
 **/
@Data
@Slf4j
public class SCPReplySrMp extends ReplyBody {
    private int first;				// number of the first Monitor Point
    private int count;				// number of MP status entries
    private List<Integer> status;			// MP status (trl07 encoded)

    @Override
    public void process(QueueProducer queueProducer, int scpId) {
//        if ( count == 1 && status.size() > 0) {
//            int cosState = status.get(0);
//            int deviceStatus = ScpMessageService.getPointStatus(cosState);
//            int tranCode = ScpMessageService.getTranCodeByCosState(cosState);
//
//            AlarmMessage sMessage = new AlarmMessage(
//                    -1, System.currentTimeMillis(), scpId,
//                    Constants.tranSrcMP, first, 0x07, tranCode, deviceStatus,
//                    Constants.TRAN_TABLE_SRC_MP,
//                    this.toString(),
//                    cosState);
//            queueProducer.sendStatusMessage(sMessage);
//        } else {
////        // 逻辑编号未必是连续的
//            String info = "FirstMp:" + first + ", ";
//            for (int i = 0; i < count; i++) {
//                info += (first + i) + ":" + status.get(i);
//            }
//            log.error("非法报警点状态上报：{}", info);
//        }
    }


//    /** 最终统一状态： 0 - 离线/无效  1 - 在线/正常  2 - 报警  3 - 故障 4 - 打开  5 - 关闭  6 - 在线（持续状态判断） */
//    int TRAGET_STATE_INVALID = 0;
//    int TRAGET_STATE_VALID = 1;
//    int TRAGET_STATE_WARN = 2;
//    int TRAGET_STATE_FAULT = 3;
//    int TRAGET_STATE_OPEN = 4;
//    int TRAGET_STATE_CLOSE = 5;
//    int TRAGET_STATE_CONTINUOUSLY = 6;
    private int getDeviceStateForMp(int mpStatus) {
        int targetState = Constants.TRAGET_STATE_INVALID;
        if ((mpStatus & 0x08) > 0 || (mpStatus & 0x80) > 0) {   // offline || not attach
            targetState = Constants.TRAGET_STATE_INVALID;
        } else if ((mpStatus & 0x20) > 0 || (mpStatus & 0x40) > 0) {   //

        }


        return targetState;
    }

}
