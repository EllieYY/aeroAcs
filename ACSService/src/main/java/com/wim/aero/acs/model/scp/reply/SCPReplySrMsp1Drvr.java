package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @title: SCPReplySrMsp1Drvr
 * @author: Ellie
 * @date: 2022/04/13 14:58
 * @description:
 **/
@Data
@Slf4j
public class SCPReplySrMsp1Drvr extends ReplyBody {
    private int number;				// MSP1 driver number (always 0 for SCP-2)
    private int port;					// actual hardware port number (0, 1, ...)
    private int mode;					// mode: 0 == disabled, 1 == enabled
    private long  baud_rate;			// (word) baud rate  eg.: 1200, ..., 38400
    private int throughput;			// i/o transactions per second (approx)

    @Override
    public void process(QueueProducer queueProducer, int scpId) {
//        log.info("{}:{}", scpId, this.toString());

//        LogMessage message = new LogMessage(
//                0, System.currentTimeMillis(), scpId,
//                Constants.tranSrcScpCom, number, Constants.customTranType, 0, this.toString());
//        queueProducer.sendLogMessage(message);
    }
}
