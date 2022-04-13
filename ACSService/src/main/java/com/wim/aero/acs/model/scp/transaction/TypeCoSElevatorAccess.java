package com.wim.aero.acs.model.scp.transaction;

import lombok.Data;

import java.util.List;

/**
 * @title: TypeCoSElevatorAccess
 * @author: Ellie
 * @date: 2022/04/01 15:30
 * @description:
 * transaction codes for tagTypeCoSElevatorAccess:
 * 1 - Elevator Access
 **/
@Data
public class TypeCoSElevatorAccess extends TransactionBody {
    private long cardholderId;
    private List<Byte> floors;
    private byte nnCardFormat;
}
