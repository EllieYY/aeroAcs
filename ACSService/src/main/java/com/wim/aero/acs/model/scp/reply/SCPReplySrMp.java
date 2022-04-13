package com.wim.aero.acs.model.scp.reply;

import lombok.Data;

/**
 * @title: SCPReplySrMp
 * @author: Ellie
 * @date: 2022/04/13 11:33
 * @description:
 **/
@Data
public class SCPReplySrMp extends ReplyBody {
    private int first;				// number of the first Monitor Point
    private int count;				// number of MP status entries
    private String status;			// MP status (trl07 encoded)
}
