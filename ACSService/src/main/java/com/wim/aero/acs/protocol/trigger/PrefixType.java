package com.wim.aero.acs.protocol.trigger;

import com.wim.aero.acs.message.OperationType;

import java.util.Arrays;

/**
 * @title: PrefixType
 * @author: Ellie
 * @date: 2022/05/24 11:21
 * @description:
 **/
public enum PrefixType {
    PREFIX_0(0, 2),
    PREFIX_256(0, 4),
    PREFIX_512(0, 5),
    PREFIX_1024(0, 6),
    UNKNOWN(0, 1);

    public int getPrefix() {
        return prefix;
    }

    private int prefix;

    public int getExecuteCode() {
        return executeCode;
    }

    private int executeCode;

    PrefixType(int prefix, int executeCode) {
        this.prefix = prefix;
        this.executeCode = executeCode;
    }

    public static PrefixType fromPrefix(int prefix){
        return Arrays.asList(PrefixType.values()).stream()
                .filter(command -> command.getPrefix() == prefix)
                .findFirst().orElse(PrefixType.UNKNOWN);
    }

    public static boolean isValidPrefix(int prefix) {
        PrefixType[] values = values();
        for (PrefixType type : values) {
            if(type.prefix == prefix){
                return true;
            }
        }
        return false;
    }
}
