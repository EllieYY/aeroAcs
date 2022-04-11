package com.wim.aero.acs.model.scpmessage;

import lombok.Data;

/**
 * @title: TypeArea
 * @author: Ellie
 * @date: 2022/04/01 15:39
 * @description:
 *
 * transaction codes for tranTypeArea
 * 1 - area disabled
 * 2 - area enabled
 * 3 - occupancy count reached zero
 * 4 - occupancy count reached the "downward-limit"
 * 5 - occupancy count reached the "upward-limit"
 * 6 - occupancy count reached the "max-occupancy-limit"
 * 7 - multi-occupancy mode change
 * 8 - multi-occupancy mode change could not be made - the area is not empty
 **/
@Data
public class TypeArea extends TransactionBody {
    //		1 - set if area is enabled (open)
    //     -- - the multi-occupancy mode is coded using bit-1 and bit 2
    //      0 - (both bit-1 and bit-2 are zero) multi-occupancy mode not enabled
    //      2 - (bit-1 is set, bit-2 is zero)  "standard" multiple occupancy rules
    //      4 - (bit-2 is set, bit-1 is zero)  "modified-1-man" multiple occupancy rules
    //      6 - (both bit-1 and bit 2 are set) "modified-2-man" multiple occupancy rules
    //	  128 - set if this area has NOT been configured (no area checks are made!!!)
    private int status;				// area status mask flags

    private long occupancy;			// occupancy count - standard users
    private long occSpc;				// occupancy count - special users
    private int priorStatus;			// flags before change


}
