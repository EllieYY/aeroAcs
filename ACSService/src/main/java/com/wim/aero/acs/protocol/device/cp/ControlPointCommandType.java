package com.wim.aero.acs.protocol.device.cp;

public enum ControlPointCommandType {
    Off(1),
    On(2),
    SinglePulse(3);

    private int code;
    ControlPointCommandType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
