package com.wim.aero.acs.model.scp.reply;

import lombok.Data;

/**
 * @title: SCPReplySrCp
 * @author: Ellie
 * @date: 2022/04/13 11:34
 * @description:
 **/
@Data
public class SCPReplySrCp {
    private int first;				// number of the first Control Point
    private int count;				// number of CP status entries
    private String status;			// CP status (trl07 encoded)
}
