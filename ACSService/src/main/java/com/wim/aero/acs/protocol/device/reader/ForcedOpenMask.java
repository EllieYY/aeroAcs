package com.wim.aero.acs.protocol.device.reader;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * @title: ForcedOpenMask
 * @author: Ellie
 * @date: 2022/03/14 10:02
 * @description: 设置or取消。8.9 Command 309: Forced Open Mask
 **/
@Data
public class ForcedOpenMask extends Operation {
    @CmdProp(index = 2)
    private int scpNumber;

    @CmdProp(index = 3)
    private int acrNumber;

    @CmdProp(index = 4)
    private int setClear;   // 1 - set mask  0 - clear mask

    public ForcedOpenMask(int scpNumber, int acrNumber, int setClear) {
        this.scpNumber = scpNumber;
        this.acrNumber = acrNumber;
        this.setClear = setClear;
    }
}
