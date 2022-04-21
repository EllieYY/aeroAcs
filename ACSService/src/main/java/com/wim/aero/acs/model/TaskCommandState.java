package com.wim.aero.acs.model;

public enum TaskCommandState {
    INIT("0"),
    DOING("1"),
    SUCCESS("2"),
    FAIL("3");

    private String code;
    TaskCommandState(String code) {
        this.code = code;
    }

    public String value() {
        return code;
    }
}
