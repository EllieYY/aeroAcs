package com.wim.aero.acs.protocol.card;

import com.wim.aero.acs.message.Operation;
import lombok.Data;

/**
 * @title: CardDelete
 * @author: Ellie
 * @date: 2022/03/14 14:28
 * @description: 10.7 Command 3305: Card Delete
 **/
@Data
public class CardDelete extends Operation {
    private int scpNumber;
    private int cardholderId;
}
