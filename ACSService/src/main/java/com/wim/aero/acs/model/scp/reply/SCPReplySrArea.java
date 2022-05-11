package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @title: SCPReplySrArea
 * @author: Ellie
 * @date: 2022/04/13 11:34
 * @description:
 **/
@Data
@Slf4j
public class SCPReplySrArea extends ReplyBody {
    private int number;				// Area number
    private int flags;				// status map
                                    //		1 - set if area is enabled (open)
                                    //     -- - the multi-occupancy mode is coded using bit-1 and bit 2
                                    //      0 - (both bit-1 and bit-2 are zero) multi-occupancy mode not enabled
                                    //      2 - (bit-1 is set, bit-2 is zero)  "standard" multiple occupancy rules
                                    //      4 - (bit-2 is set, bit-1 is zero)  "modified-1-man" multiple occupancy rules
                                    //      6 - (both bit-1 and bit 2 are set) "modified-2-man" multiple occupancy rules
                                    //	  128 - set if this area has NOT been configured (no area checks are made!!!)
    private long  occupancy;			// occupancy count - standard users
    private long  occ_spc;				// occupancy count - special users

    @Override
    public void process(QueueProducer queueProducer, int scpId) {
        if (flags != 128) {
            log.info("occupancy更新：scpId[{}], acr[{}], occupancyStd[{}], occupancySpc[{}]",
                    scpId, number, occupancy, occ_spc);

            // TODO:更新数据库
        }

//        LogMessage message = new LogMessage(
//                0, System.currentTimeMillis(), scpId,
//                Constants.tranTypeMpg, number, Constants.customTranType, 0, this.toString());
//        queueProducer.sendLogMessage(message);

    }
}
