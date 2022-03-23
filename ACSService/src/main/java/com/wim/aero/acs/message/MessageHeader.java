package com.wim.aero.acs.message;

import lombok.Data;

/**
 * @title: MessageHeader
 * @author: Ellie
 * @date: 2022/02/10 11:26
 * @description:
 **/
@Data
public class MessageHeader {
    private int scpId;
    private int msgType;
}
