package com.wim.aero.acs.model.scpmessage;

/**
 * @title: TypeCoSElevator
 * @author: Ellie
 * @date: 2022/04/01 15:33
 * @description:
 *
 * transaction codes for tagTypeCoSElevator:
 * 1 - Floor Status is Secure
 * 2 - Floor Status is Public
 * 3 - Floor Status is Disabled
 **/
public class TypeCoSElevator {
    private byte prevFloorStatus;
    private byte floorNumber;
}
