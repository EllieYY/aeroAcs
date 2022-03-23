package com.wim.aero.acs.protocol.timezone;

import lombok.Data;

/**
 * @title: TimeZoneControl
 * @author: Ellie
 * @date: 2022/03/10 14:50
 * @description: 9.4 Command 314: Time Zone Control
 **/
@Data
public class TimeZoneControl {
    private int scpNumber;
    private int tzNumber;
    private int command;
}
