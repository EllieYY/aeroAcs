package com.wim.aero.acs.protocol.device;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @title: ScpReboot
 * @author: Ellie
 * @date: 2022/05/31 16:38
 * @description: 18.12 Command 913: Apply/Reboot
 **/
@Data
@AllArgsConstructor
public class ScpReboot extends Operation {
    @CmdProp(index = 2)
    private int scpId;
}
