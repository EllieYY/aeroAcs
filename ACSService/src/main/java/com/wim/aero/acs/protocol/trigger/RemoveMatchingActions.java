package com.wim.aero.acs.protocol.trigger;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;
import net.sf.jsqlparser.statement.select.First;

import java.util.Set;

/**
 * @title: RemoveMatchingActions
 * @author: Ellie
 * @date: 2022/05/20 14:40
 * @description: 12.5 Command 119: Remove Matching Actions from Range
 **/
@Data
public class RemoveMatchingActions extends Operation {
    @CmdProp(index = 2)
    private int lastModified = 0; // Set to 0

    @CmdProp(index = 3)
    private int scpNumber;   // SCP number

    @CmdProp(index = 4)
    private int cProcFirst;   // First procedure in range

    @CmdProp(index = 5)
    private int cProcLast;   // Last procedure in range

    @CmdProp(index = 6)
    private int cActionType;
}
