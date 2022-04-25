package com.wim.aero.acs.model.scp;

import com.wim.aero.acs.model.scp.reply.EnScpReplyType;

import java.util.function.Predicate;

/**
 * scp状态
 */
public enum ScpStatus {
    UNKNOWN(0),    // 初始化
    INIT(1),
    OFF_LINE(2),   // 离线
    ON_LINE(3),    // 在线
    CONFIG(4);     // 配置成功

    ScpStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
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
