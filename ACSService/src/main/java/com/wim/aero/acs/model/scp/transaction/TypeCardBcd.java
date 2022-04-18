package com.wim.aero.acs.model.scp.transaction;

/**
 * @title: TypeCardBcd
 * @author: Ellie
 * @date: 2022/04/18 17:52
 * @description:
 * // transaction codes for tranTypeCardBcd:
 * //		1 - access denied, invalid card format, forward read
 * //		2 - access denied, invalid card format, reverse read
 **/
public class TypeCardBcd extends TransactionBody implements AccessEvent {
    private int digit_count;			// number of valid digits (0-9 plus A-F)
    private String bcd_array;		// each entry holds a hex digit: 0x0 - 0xF

    @Override
    public String getCardHolder() {
        return bcd_array.substring(0, digit_count);
    }
}
