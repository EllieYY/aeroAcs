package com.wim.aero.acs.protocol.accessLevel;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * @title: ElevatorALsSpecification
 * @author: Ellie
 * @date: 2022/03/11 12:17
 * @description: 16.5 Command 501: Elevator Access Level Specification
 **/
@Data
public class ElevatorALsSpecification extends Operation {
    @CmdProp(index = 2)
    private int lastModified = 0;

    @CmdProp(index = 3)
    private int scpNumber;

    @CmdProp(index = 4)
    private int maxElalvl = 256; // Maximum number of elevator access level to create. Maximum is 256.

    @CmdProp(index = 5)
    private int maxFloors;

    public ElevatorALsSpecification(int scpNumber, int maxFloors) {
        this.scpNumber = scpNumber;
        this.maxFloors = maxFloors;
    }
}
