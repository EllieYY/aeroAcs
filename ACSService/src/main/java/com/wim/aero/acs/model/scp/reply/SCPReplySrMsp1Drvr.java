package com.wim.aero.acs.model.scp.reply;

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
    public void process(int scpId) {
        log.info("{}:{}", scpId, this.toString());

        // TODO:更新数据库or推送Mq：在线状态
        if (mode == 1) {

        }
    }
}
