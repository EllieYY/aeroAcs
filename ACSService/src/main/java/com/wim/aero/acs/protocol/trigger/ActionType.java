package com.wim.aero.acs.protocol.trigger;

import com.wim.aero.acs.protocol.device.cp.ControlPointCommandType;
import net.sf.jsqlparser.statement.select.Wait;
import org.apache.activemq.command.Command;
import org.springframework.scheduling.Trigger;

import java.awt.geom.Area;
import java.io.Reader;
import java.util.Arrays;
import java.util.Set;

public enum ActionType {
    DEL_ALL(0, 0, 0),
    CMD_306(1, 306, 3),  //  1 Command 306: Monitor Point Mask    // 2
    CMD_307(2, 307, 6),  // 2 Command 307: Control Point Command  // 2
    CMD_308(3, 308, 3),  // 3 Command 308: ACR Mode
    CMD_309(4, 309, 3),  // 4 Command 309: Forced Open Mask
    CMD_310(5, 310, 3),  // 5 Command 310: Held Open Mask Control
    CMD_311(6, 311, 6),  // 6 Command 311: Momentary Unlock
    CMD_312(7, 312, 3),  // 7 Command 312: Procedure Control Command
    CMD_313(8, 313, 3),  // 8 Command 313: Trigger Variable Control Command
    CMD_314(9, 314, 3),  // 9 Command 314: Time Zone Control
    CMD_315(10, 315, 3),  // 10 Command 315: java.io.Reader LED Mode Control
    CMD_3319(11, 3319, 4), // 11 Command 3319:Anti- passback Control Command to all or individual (currently allows only free pass to all)
    // 12 Not used

    CMD_321(14, 321, 4), // 14 Command 321: Monitor Point Group Arm/Disarm Command
    MASK_COUNT_PREFIX(15, 0,4),   // 15 Set "action_type" prefix based on "mask_count". See remarks.
    ACTIVE_PT_PREFIX(16, 0,4),    // 16 Set "action_type" prefix based on "active points". See remarks.
    CMD_322(17, 322, 4), // 17 Command 322: Modify Access Area Configuration
    ABORT_WFDO(18, 0,0),   // 18 Abort the Wait-For-Door-Open state - generally used in turnstile mode to abort pending access requests.
//    CMD_325(19, 12),  // 19 Command 325: Temporary Reader LED Control
//    CMD_326(20, 11),//20 Command 326: Text Output o to an LCD Terminal

    //21 Not used
    //23 Not used

    CMD_334(24, 334, 5), // 24 Command 334: Temp ACR Mode
    CMD_331(25, 331, 8), // 25 Command 331: Host Simulated Card Read
    CMD_3323(26, 3323, 3),    // 26 Command 3323: Set Cardholder's Use Limit (currently only the all option functions)
//    CMD_335(27, 335, 4),  // 27 Command 335: Set Operating Mode
    CMD_339(28, 339,4),  // 28 Command 339: Host Simulated Key Press
    FILTER_OUT_TRANS(29, 0,0),    // 29 Filter out transaction in calling trigger.
                                            // No additional parameters necessary. Command 1820:
                                            // Trigger Activation Summary or action type 30 can be used to return a summary of filtered
                                            // transactions.
//    CMD_1820(30, 1820,3), // 30 Command 1820: Trigger Activation Summary
    DELAY_IN_1_10_S(126, 0,1),  // 126 Delay in 0.1 second increments. See remarks.
    DELAY_IN_S(127, 0,1),   // 127 Delay in seconds. See remarks.
    UNKNOWN(-1,0,0);


    public int getType() {
        return type;
    }

    private int type;
    private int opCode;
    private int length;

    ActionType(int type, int opCode, int length) {
        this.type = type;
        this.opCode = opCode;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public static ActionType fromType(int code){
        return Arrays.asList(ActionType.values()).stream()
                .filter(command -> command.getType() == code)
                .findFirst().orElse(ActionType.UNKNOWN);
    }
}
