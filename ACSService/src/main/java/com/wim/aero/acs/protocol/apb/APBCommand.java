package com.wim.aero.acs.protocol.apb;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * @title: APBCommand
 * @author: Ellie
 * @date: 2022/03/14 14:31
 * @description: 新用户的APB初始化。10.8 Command 3319:Anti-passback Control Command
 **/
@Data
public class APBCommand extends Operation {
    @CmdProp(index = 2)
    private int scpNumber;

    @CmdProp(index = 3)
    private int cardholderId;  //  -1 to specify all cardholders

    @CmdProp(index = 4)
    private int apb_area; // 0 all free pass at an APB controlled ACR.


    /**
     * 0 - 不使用
     * 1 - 卡记录不存在时不产生NAK
     * 2 - 指定允许所有卡free pass时，移动到area 0.
     * 3 - 1和2都生效
     */
    @CmdProp(index = 5)
    private int flags;

}
