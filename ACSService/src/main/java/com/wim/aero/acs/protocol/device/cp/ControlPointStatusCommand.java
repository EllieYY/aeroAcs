package com.wim.aero.acs.protocol.device.cp;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @title: CpStatusCommand
 * @author: Ellie
 * @date: 2022/06/14 13:42
 * @description: 11.18 406
 **/
@Data
@AllArgsConstructor
public class ControlPointStatusCommand extends Operation {
    @CmdProp(index = 1)
    private int scpId;

    @CmdProp(index = 2)
    private int first;

    @CmdProp(index = 3)
    private int count;   // max 100
}
