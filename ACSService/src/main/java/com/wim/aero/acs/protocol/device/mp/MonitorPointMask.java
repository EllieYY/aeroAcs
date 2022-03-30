package com.wim.aero.acs.protocol.device.mp;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * @title: MonitorPointMask
 * @author: Ellie
 * @date: 2022/03/30 14:43
 * @description: 一键设防和撤防 7.5 Command 306: Monitor Point Mask
 **/
@Data
public class MonitorPointMask extends Operation {
    @CmdProp(index = 2)
    private int scpId;

    @CmdProp(index = 3)
    private int mpNumber;

    @CmdProp(index = 4)
    private int setClear;   // 非零撤防

    public MonitorPointMask(int scpId, int mpNumber, boolean isMask) {
        this.scpId = scpId;
        this.mpNumber = mpNumber;
        this.setClear = (isMask ? 1 : 0);
    }
}
