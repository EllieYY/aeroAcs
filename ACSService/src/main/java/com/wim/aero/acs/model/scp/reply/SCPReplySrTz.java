package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.service.QueueProducer;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @title: SCPReplySrTz
 * @author: Ellie
 * @date: 2022/04/13 15:00
 * @description:
 **/
@Data
@Slf4j
public class SCPReplySrTz extends ReplyBody {
    private int first;				// number of the first Timezone
    private int count;				// number of TZ status entries
    private List<Integer> status;			// 100 - TZ status is bit-mapped:
                                    // 0x01 mask == tz active
                                    // 0x02 mask == time based scan state
                                    // 0x04 mask == time scan override

    @Override
    public void process(QueueProducer queueProducer, int scpId) {
//        LogMessage message = new LogMessage(
//                0, System.currentTimeMillis(), scpId,
//                Constants.tranSrcTimeZone, first, Constants.customTranType, 0, this.toString());
//        queueProducer.sendLogMessage(message);
    }
}
