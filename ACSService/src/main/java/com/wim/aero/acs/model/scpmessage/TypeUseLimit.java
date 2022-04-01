package com.wim.aero.acs.model.scpmessage;

/**
 * @title: TypeUseLimit
 * @author: Ellie
 * @date: 2022/04/01 15:36
 * @description:
 *
 * transaction codes for tranUseLimit:
 * 1 - use limit changed, reporting new limit
 **/
public class TypeUseLimit {
    private int ueeCount;				// the updated use count as a result of this access
    private long cardholderId;			// cardholder ID number
}
