package com.wim.aero.acs.model.mq;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @title: ScpMacMessage
 * @author: Ellie
 * @date: 2022/08/02 11:10
 * @description:
 **/
@Data
@AllArgsConstructor
public class ScpMacMessage {
    private int scpId;
    private String macStr;
}
