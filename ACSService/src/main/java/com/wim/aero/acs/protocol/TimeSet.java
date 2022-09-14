package com.wim.aero.acs.protocol;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.Date;

/**
 * @title: TimeSet
 * @author: Ellie
 * @date: 2022/03/14 16:13
 * @description: 时间同步。4.4 Command 302: Time Set
 **/
@Data
public class TimeSet extends Operation {
    @CmdProp(index = 2)
    private int scpNumber;

    @CmdProp(index = 3)
    private long customTime; // 0 - 不使用， else - 到1970的秒数

    public TimeSet(int scpNumber) {
        this.scpNumber = scpNumber;

//        customTime = System.currentTimeMillis() / 1000;
    }
}
