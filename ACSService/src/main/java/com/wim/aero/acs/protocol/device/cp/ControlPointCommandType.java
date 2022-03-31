package com.wim.aero.acs.protocol.device.cp;

import java.util.Arrays;

public enum ControlPointCommandType {
    OFF(1),
    ON(2),
    SINGLE_PULSE(3),
    REPEATING_PULSE(4),
    UNKNOWN(-1);

    private int code;
    ControlPointCommandType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ControlPointCommandType fromTypeCode(int code){
        return Arrays.asList(ControlPointCommandType.values()).stream()
                .filter(command -> command.getCode() == code)
                .findFirst().orElse(ControlPointCommandType.UNKNOWN);
    }
}
