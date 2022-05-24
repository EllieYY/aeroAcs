package com.wim.aero.acs.protocol.trigger;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;
import org.springframework.scheduling.Trigger;

/**
 * @title: TriggerVariableControl
 * @author: Ellie
 * @date: 2022/05/20 14:31
 * @description: 12.7 Command 313: Trigger Variable Control Command
 **/
@Data
public class TriggerVariableControl  extends Operation {
    @CmdProp(index = 2)
    private int scpNumber;   // SCP number

    @CmdProp(index = 3)
    private int tvNumber;    // org.springframework.scheduling. Trigger variable number to set or clear (1to 127)

    @CmdProp(index = 4)
    private int setClear;    // Trigger variable value to set. Use zero to clear.
}
