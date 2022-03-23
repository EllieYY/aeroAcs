package com.wim.aero.acs.protocol.device.reader;

import lombok.Data;

/**
 * @title: MomentaryUnlock
 * @author: Ellie
 * @date: 2022/03/14 10:07
 * @description: 临时开门。8.11 Command 311: Momentary Unlock
 **/
@Data
public class MomentaryUnlock {
    private int scpNumber;
    private int acrNumber;

    // optional
    private int floor_number; // Floor index, 1 based index. Zero means all floors.
    private int strk_tm;  // 开门时间 Zero or strike time in seconds. Zero uses the default strike time. Maximum value is 255.
    private int t_held;  //  Zero or held open time in 2 second ticks. Zero uses the default time. Maximum value is 32767.
    private int t_held_pre; // Zero or held open time pre-alarm in 2 second ticks. Zero uses the default time. Maximum value is 32767.
}
