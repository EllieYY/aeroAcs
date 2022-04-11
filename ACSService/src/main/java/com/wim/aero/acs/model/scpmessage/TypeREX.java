package com.wim.aero.acs.model.scpmessage;

import lombok.Data;

/**
 * @title: TypeREX
 * @author: Ellie
 * @date: 2022/04/01 16:05
 * @description: 开门按钮
 *
 * transaction codes for tranTypeREX
 * 1 - exit cycle: door use not verified
 * 2 - exit cycle: door not used
 * 3 - exit cycle: door used
 * 4 - host initiated request: door use not verified
 * 5 - host initiated request: door not used
 * 6 - host initiated request: door used
 * 7 - exit request denied: Airlock Busy
 * 8 - host request: Cannot complete due to Airlock Busy.
 * 9 - exit cycle: started
 **/
@Data
public class TypeREX extends TransactionBody {
    private int rex_number;				// rex that initiated the request (0 or 1)
}
