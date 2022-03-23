package com.wim.aero.acs.protocol.accessLevel;

import com.wim.aero.acs.message.Operation;
import lombok.Data;

/**
 * @title: ElevatorALsSpecification
 * @author: Ellie
 * @date: 2022/03/11 12:17
 * @description: 16.5 Command 501: Elevator Access Level Specification
 **/
@Data
public class ElevatorALsSpecification extends Operation {
    private int lastModified = 0;
    private int scpNumber;
    private int maxElalvl; // Maximum number of elevator access level to create. Maximum is 256.
    private int maxFloors;
}
