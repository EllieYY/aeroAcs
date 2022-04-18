package com.wim.aero.acs.model.scp.transaction;

/**
 * @title: TypeCardBin
 * @author: Ellie
 * @date: 2022/04/18 17:48
 * @description:
 *
 * // transaction codes for tranTypeCardBin:
 * //		1 - access denied, invalid card format
 * // 		2 - access granted, invalid card format
 **/
public class TypeCardBin extends TransactionBody implements AccessEvent {
    private int bit_count;			// number of valid data bits
    private String bit_array;		// first bit is (0x80 & bit_array[0])

    @Override
    public String getCardHolder() {
        return bit_array.substring(0, bit_count);
    }
}
