package com.wim.aero.acs.protocol.device;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * @title: ScpReset
 * @author: Ellie
 * @date: 2022/03/31 08:32
 * @description: 14.4 Command 301:Reset SCP
 **/
@Data
public class ScpReset extends Operation {
    @CmdProp(index = 2)
    private int scpId;

    public ScpReset(int scpId) {
        this.scpId = scpId;
    }
}
