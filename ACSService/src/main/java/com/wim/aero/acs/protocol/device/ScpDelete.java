package com.wim.aero.acs.protocol.device;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;


/**
 * @title: ScpDetach
 * @author: Ellie
 * @date: 2022/05/05 15:14
 * @description: 3.4 Command 015: Delete SCP
 **/
@Data
public class ScpDelete extends Operation {
    @CmdProp(index = 2)
    private int nSCPId;

    public ScpDelete(int nSCPId) {
        this.nSCPId = nSCPId;
    }
}
