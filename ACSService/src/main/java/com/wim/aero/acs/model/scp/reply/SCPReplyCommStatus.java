package com.wim.aero.acs.model.scp.reply;

import lombok.Data;

/**
 * @title: SCPReplyCommStatus
 * @author: Ellie
 * @date: 2022/04/13 11:34
 * @description:
 **/
@Data
public class SCPReplyCommStatus extends ReplyBody {
    private int status;   // enSCPComm
    private int  error_code;   // enSCPCommErr
    private int  nChannelId;			// channel number - valid if status==enSCPComNoError
    // extended reporting for DualPort applications
    private int	current_primary_comm;		// 0 == detached, 1 == not tried, 2 == off-line, 3 == on-line
    private int	previous_primary_comm;		// 0 == detached, 1 == not tried, 2 == off-line, 3 == on-line
    private int	current_alternate_comm;		// Not used
    private int	previous_alternate_comm;	// Not used
}
