package com.wim.aero.acs.protocol.timezone;

public enum TimeZoneCommand {
    Temporary_Clear(1),
    Temporary_Set(2),
    Override_Clear(3),
    Override_Set(4),
    Release(5),
    Refresh(6);

    private int code;

    TimeZoneCommand(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
