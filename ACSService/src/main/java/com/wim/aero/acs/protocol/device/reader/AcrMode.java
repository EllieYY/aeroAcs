package com.wim.aero.acs.protocol.device.reader;

import java.util.Arrays;

public enum AcrMode {
    DISABLE(1),
    UNLOCK(2),
    LOCKED(3),
    FACILITY_CODE_ONLY(4),
    CARD_ONLY(5),
    PIN_ONLY(6),
    CARD_AND_PIN(7),
    CARD_OR_PIN(8),
    ACR_F_2CARD_CLEAR(16),
    ACR_F_2CARD(17),
    ACR_FE_NO_ARQ_CLEAR(26),
    ACR_FE_NO_ARQ(27),
    ACR_FE_LINK_MODE(29),
    ACR_FE_LINK_MODE_CLEAR(30),
    EX_FEATURE_CHANGE(31),
    ACR_FE_LINK_MODE_ALT(32),
    ACR_FE_LINK_MODE_ALT_CLEAR(33),
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
