package com.wim.aero.acs.model.scp;

import com.wim.aero.acs.model.scp.reply.EnScpReplyType;

import java.util.function.Predicate;

/**
 * scp状态
 */
public enum ScpStatus {
    UNKNOWN(0),    // 初始化
    OFF_LINE(1),   // 离线
    ON_LINE(2),    // 在线
    CONFIG(3);     // 配置成功

    ScpStatus(int status) {
        this.status = status;
    }
    private int status;

    public static ScpStatus fromCode(int code){
        return getTransactionType(requestType -> (requestType.status == code));
    }

    private static ScpStatus getTransactionType(Predicate<ScpStatus> predicate){
        ScpStatus[] values = values();
        for (ScpStatus operationType : values) {
            if(predicate.test(operationType)){
                return operationType;
            }
        }

        return UNKNOWN;
    }
}
