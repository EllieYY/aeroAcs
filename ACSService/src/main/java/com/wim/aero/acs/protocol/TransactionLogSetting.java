package com.wim.aero.acs.protocol;

import lombok.Data;

/**
 * @title: TransactionLogSetting
 * @author: Ellie
 * @date: 2022/03/14 16:09
 * @description: 11.8 Command 303: Set the Transaction Log Index
 * 和SCP建立通信并正确配置之前，在1013种设置关闭，坚立通信之后，通过此命令打开。
 **/
@Data
public class TransactionLogSetting {
    private int scpNumber;
    private int tranIndex;  // -1 disable  -2 enable
}
