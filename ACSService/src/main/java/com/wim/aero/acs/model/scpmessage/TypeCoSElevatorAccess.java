package com.wim.aero.acs.model.scpmessage;

import java.util.List;

/**
 * @title: TypeCoSElevatorAccess
 * @author: Ellie
 * @date: 2022/04/01 15:30
 * @description:
 * transaction codes for tagTypeCoSElevatorAccess:
 * 1 - Elevator Access
 **/
public class TypeCoSElevatorAccess {
    private long cardholderId;
    private List<Byte> floors;
    private byte nnCardFormat;
}
