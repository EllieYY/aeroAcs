package com.wim.aero.acs.model.scp.reply;

/**
 * @title: SCPReplySrMp
 * @author: Ellie
 * @date: 2022/04/13 11:33
 * @description:
 **/
public class SCPReplySrMp {
    private int first;				// number of the first Monitor Point
    private int count;				// number of MP status entries
    private String status;			// MP status (trl07 encoded)
}
