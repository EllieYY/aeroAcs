package com.wim.aero.acs.protocol.device.cp;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * @title: ControlPointCommand
 * @author: Ellie
 * @date: 2022/03/30 15:04
 * @description: 7.6 Command 307: Control Point Command
 **/
@Data
public class ControlPointCommand extends Operation {
    @CmdProp(index = 2)
    private int scpNumber;

    @CmdProp(index = 3)
    private int cpNumber; // Control Point number

    @CmdProp(index = 4)
    private int command; // Command mode code:

    @CmdProp(index = 5)
    private int onTime;

    @CmdProp(index = 6)
    private int offTime;

    @CmdProp(index = 7)
    private int repeat;

    public ControlPointCommand(int scpNumber, int cpNumber, int command) {
        this.scpNumber = scpNumber;
        this.cpNumber = cpNumber;
        this.command = command;
    }
}
