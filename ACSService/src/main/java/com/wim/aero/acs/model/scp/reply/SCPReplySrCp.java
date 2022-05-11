package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
//        // TODO：逻辑编号未必是连续的
//        String info = "FirstCp:" + first + ", ";
//        for (int i = 0;i < count; i++) {
//            info += (first + i) + ":" + status.get(i);
//        }
//        log.info(info);
//        LogMessage message = new LogMessage(
//                0, System.currentTimeMillis(), scpId,
//                Constants.tranSrcCP, first, Constants.customTranType, 0, this.toString());
//        queueProducer.sendLogMessage(message);
    }
}
