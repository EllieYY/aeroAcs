package com.wim.aero.acs.model.scp.reply;

import lombok.Data;

/**
 * @title: SCPReplyCmndStatus
 * @author: Ellie
 * @date: 2022/04/13 11:34
 * @description:
 **/
@Data
public class SCPReplyCmndStatus extends ReplyBody {
    private int	status;				// command delivery status:
                                    // - 0 = FAILED (could not send, SCP off-line)
                                    // - 1 = OK (delivered and accepted),
                                    // - 2 = NAK'd (command rejected by the SCP)

    private long	sequence_number;	// sequence number assigned when posted

    // the following block is included only if status==2 (NAK)
    // this extension includes the information that will be returned in the following NAK reply
    private SCPReplyNAK nak;
}
