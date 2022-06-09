package com.wim.aero.acs.protocol.trigger;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * @title: TriggerSpecification
 * @author: Ellie
 * @date: 2022/05/20 14:33
 * @description: 12.2 Command 117: Trigger Specification
 **/
@Data
public class TriggerSpecification extends Operation {
    @CmdProp(index = 2)
    private Integer lastModified = 0;

    @CmdProp(index = 3)
    private Integer scpNumber;  // SCP number

    @CmdProp(index = 4)
    private Integer trgrNumber; // Trigger number

    @CmdProp(index = 5)
    private Integer command; // org.apache.activemq.command.Command to issue to the procedure:
                        //    Value Command description
                        // 1 Abort a delayed procedure
                        // 2 Execute actions with prefix 0
                        // 3 Resume a delayed procedure and execute actions with prefix 0
                        // 4 Execute actions with prefix 256
                        // 5 Execute actions with prefix 512
                        // 6 Execute actions with prefix 1024
                        // 7 Resume a delayed procedure and execute actions with prefix 256
                        // 8 Resume a delayed procedure and execute actions with prefix 512
                        // 9 Resume a delayed procedure and execute actions with prefix 1024

    @CmdProp(index = 6)
    private Integer procNum;

    @CmdProp(index = 7)
    private Integer srcType;

    @CmdProp(index = 8)
    private Integer srcNumber;

    @CmdProp(index = 9)
    private Integer tranType;

    @CmdProp(index = 10)
    private Integer tranCodeMap;

    @CmdProp(index = 11)
    private Integer timezone;

    @CmdProp(index = 12)
    private Integer var1;

    @CmdProp(index = 13)
    private Integer var2;

    @CmdProp(index = 14)
    private Integer var3;

    @CmdProp(index = 15)
    private Integer var4;

    @CmdProp(index = 16)
    private Integer arg1;

    @CmdProp(index = 17)
    private Integer arg2;

    @CmdProp(index = 18)
    private Integer arg3;

    @CmdProp(index = 19)
    private Integer arg4;

}
