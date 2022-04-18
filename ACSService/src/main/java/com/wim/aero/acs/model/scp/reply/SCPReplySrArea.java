package com.wim.aero.acs.model.scp.reply;

import lombok.Data;

/**
 * @title: SCPReplySrArea
 * @author: Ellie
 * @date: 2022/04/13 11:34
 * @description:
 **/
@Data
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
    public void process() {

    }
}
