package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.model.mq.ScpSeqMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @title: SCPReplyCmndStatus
 * @author: Ellie
 * @date: 2022/04/13 11:34
 * @description: type = 15
 **/
@Data
@Slf4j
public class SCPReplyCmndStatus extends ReplyBody {
    private int	status;				// command delivery status:
                                    // - 0 = FAILED (could not send, SCP off-line)
                                    // - 1 = OK (delivered and accepted),
                                    // - 2 = NAK'd (command rejected by the SCP)

    private long sequence_number;	// sequence number assigned when posted

    // the following block is included only if status==2 (NAK)
    // this extension includes the information that will be returned in the following NAK reply
    private SCPReplyNAKStr nak;    // SCPReplyNAK

    @Override
    public void process(QueueProducer queueProducer, int scpId) {
        // 区分成功和失败的处理
        int reason = -1;
        if (status == 2) {
            reason = Integer.parseInt(nak.getReason());
        }

//        log.info("[Command返回] {} - {}", scpId, this.toString());


        // 推送消息队列中
        queueProducer.sendScpMessage(new ScpSeqMessage(scpId, sequence_number, status, reason, this.toString()));

    }
}
