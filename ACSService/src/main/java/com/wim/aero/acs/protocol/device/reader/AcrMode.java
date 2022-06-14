package com.wim.aero.acs.protocol.device.reader;

import java.util.Arrays;

public enum AcrMode {
//    DISABLE(1),
    UNLOCK(2),
    LOCKED(3),
    FACILITY_CODE_ONLY(4),
    CARD_ONLY(5),
    PIN_ONLY(6),
    CARD_AND_PIN(7),
    CARD_OR_PIN(8),
    UNKNOWN(-1);


    private int code;
    AcrMode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static AcrMode fromTypeCode(int code){
        return Arrays.asList(AcrMode.values()).stream()
                .filter(mode -> mode.getCode() == code)
                .findFirst().orElse(AcrMode.UNKNOWN);
    }
}
