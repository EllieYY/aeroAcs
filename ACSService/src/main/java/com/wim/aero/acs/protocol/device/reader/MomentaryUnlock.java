package com.wim.aero.acs.protocol.device.reader;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * @title: MomentaryUnlock
 * @author: Ellie
 * @date: 2022/03/14 10:07
 * @description: 临时开门。8.11 Command 311: Momentary Unlock
 **/
@Data
public class MomentaryUnlock extends Operation {
    @CmdProp(index = 2)
    private int scpNumber;
    @CmdProp(index = 3)
    private int acrNumber;

    // optional
    @CmdProp(index = 4)
    private int floorMumber = 0; // Floor index, 1 based index. Zero means all floors.

    @CmdProp(index = 5)
    private int strkTm;  // 开门时间 Zero or strike time in seconds. Zero uses the default strike time. Maximum value is 255.

//    private int t_held;  //  Zero or held open time in 2 second ticks. Zero uses the default time. Maximum value is 32767.
//    private int t_held_pre; // Zero or held open time pre-alarm in 2 second ticks. Zero uses the default time. Maximum value is 32767.
}
