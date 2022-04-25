package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @title: SCPReplySrMpg
 * @author: Ellie
 * @date: 2022/04/13 11:34
 * @description:
 **/
@Data
@Slf4j
public class SCPReplySrMpg extends ReplyBody {
    private int number;							// MPG number
    private int mask_count;						// mask count
    private int num_active;						// number of active MPs
    private List<Integer> active_mp_list;	// list of the active point pairs (Type-Num) (MAX_MP_PER_MPG)

    @Override
    public void process(QueueProducer queueProducer, int scpId) {
        LogMessage message = new LogMessage(
                0, System.currentTimeMillis(), scpId,
                Constants.tranSrcMPG, number, Constants.customTranType, 0, this.toString());
        queueProducer.sendLogMessage(message);
    }
}
