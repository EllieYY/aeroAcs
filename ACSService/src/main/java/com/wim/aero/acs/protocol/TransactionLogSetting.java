package com.wim.aero.acs.protocol;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @title: TransactionLogSetting
 * @author: Ellie
 * @date: 2022/03/14 16:09
 * @description: 11.8 Command 303: Set the Transaction Log Index
 * 和SCP建立通信并正确配置之前，在1013种设置关闭，建立通信之后，通过此命令打开。
 **/
@Data
@AllArgsConstructor
public class TransactionLogSetting extends Operation {
    @CmdProp(index = 2)
    private int scpNumber;

    @CmdProp(index = 3)
    private long tranIndex;  // -1 disable  -2 enable

    public static TransactionLogSetting openLog(int scpNumber) {
        return new TransactionLogSetting(scpNumber, -2);
//        this.scpNumber = scpNumber;
    }
}
