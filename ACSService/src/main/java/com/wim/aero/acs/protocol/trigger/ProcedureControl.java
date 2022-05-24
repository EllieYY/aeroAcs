package com.wim.aero.acs.protocol.trigger;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * @title: ProcedureControl
 * @author: Ellie
 * @date: 2022/05/20 14:39
 * @description: 12.6 Command 312: Procedure Control Command
 **/
@Data
public class ProcedureControl extends Operation {
    @CmdProp(index = 2)
    private int scpNumber;   // SCP number

    @CmdProp(index = 3)
    private int proc_number;  //  Procedure number

    @CmdProp(index = 4)
    private int command;
                        //    1 Abort a delayed procedure
                        //    2 Execute actions with prefix 0
                        //    3 Resume a delayed procedure with prefix 0
                        //    4 Execute actions with prefix 256
                        //    5 Execute actions with prefix 512
                        //    6 Execute actions with prefix 1024
                        //    7 Resume a delayed procedure with prefix 256
                        //    8 Resume a delayed procedure with prefix 512
                        //    9 Resume a delayed procedure with prefix 1024


    public ProcedureControl(int scpNumber, int proc_number, int prefix) {
        this.scpNumber = scpNumber;
        this.proc_number = proc_number;
        this.command = PrefixType.fromPrefix(prefix).getExecuteCode();
    }
}
