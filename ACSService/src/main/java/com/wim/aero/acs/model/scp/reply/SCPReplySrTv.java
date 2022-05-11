package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @title: SCPReplySrTv
 * @author: Ellie
 * @date: 2022/04/13 15:01
 * @description:
 **/
@Data
@Slf4j
public class SCPReplySrTv extends ReplyBody {
    private int first;				// number of the first Trigger Variable
    private int count;				// number of TV status entries
    private List<Integer> status;	// 100 - TV status: set/clear

    @Override
    public void process(QueueProducer queueProducer, int scpId) {
//        LogMessage message = new LogMessage(
//                0, System.currentTimeMillis(), scpId,
//                Constants.tranSrcTrigger, first, Constants.customTranType, 0, this.toString());
//        queueProducer.sendLogMessage(message);

    }
}
