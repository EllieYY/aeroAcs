package com.wim.aero.acs.model.scp.transaction;

import lombok.Data;

/**
 * @title: TypeCoS
 * @author: Ellie
 * @date: 2022/04/01 15:05
 * @description: 0x07 change-of-state
 *
 * transaction codes for tranTypeCoSDoor:
 * 1 - disconnected
 * 2 - unknown (_RS bits: last known status)
 * 3 - secure
 * 4 - alarm (forced, held, or both)
 * 5 - fault (fault type is encoded in door_status byte
 * 6 - Exit delay in progress
 * 7 - Entry delay in progress
 **/
@Data
public class TypeCoS extends TransactionBody {
    // status code byte encoding:
    //		0x07 - status mask: 0=inactive, 1=active, 2-7=supervisory fault codes:
    //				2==ground, 3==short, 4==open, 5==foreign voltage, 6==non-settling
    //		0x08 - off-line: comm to the input point is not valid
    //		0x10 - mask flag: set if the Monitor Point is MASK-ed
    //		0x20 - local mask flag: entry or exit delay in progress
    //		0x40 - entry delay in progress
    //		0x80 - not attached (the Monitor Point is not linked to an Input)
    private byte status;		 // new status
    private byte old_sts;		 // previous status (prior to this CoS report)

}
