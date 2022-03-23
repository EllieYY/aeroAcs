package com.wim.aero.acs.protocol.device.reader;

import lombok.Data;

/**
 * @title: ForcedOpenMask
 * @author: Ellie
 * @date: 2022/03/14 10:02
 * @description: 设置or取消。8.9 Command 309: Forced Open Mask
 **/
@Data
public class ForcedOpenMask {
    private int scpNumber;
    private int acrNumber;
    private int setClear;   // 1 - set  0 - mask
}
