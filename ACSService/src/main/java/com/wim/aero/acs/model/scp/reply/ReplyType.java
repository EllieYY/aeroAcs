package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.model.scp.transaction.TransactionBody;
import com.wim.aero.acs.model.scp.transaction.TypeCoS;
import com.wim.aero.acs.model.scp.transaction.TypeSioComm;

import java.util.function.Predicate;

/**
 * @title: OperationType
 * @author: Ellie
 * @date: 2022/02/10 14:03
 * @description:
 **/
public enum ReplyType {

    SCP_LCL_COS(0x04, TypeCoS.class),
    SIO_COMM_COS(0x04, TypeSioComm.class),
    SIO_TMPR_COS(0x05, TypeCoS.class),
    SIO_PWR_COS(0x06, TypeCoS.class),
    SIO_MP_COS(0x07, TypeCoS.class),
    SIO_CP_COS(0x08, TypeCoS.class),
    ACR_ALT_TMPR_COS(0x15, TypeCoS.class);

    public int getTypeCode() {
        return typeCode;
    }

    private int typeCode;
    private Class<? extends TransactionBody> transClazz;

    ReplyType(int typeCode, Class<? extends TransactionBody> transClazz) {
        this.typeCode = typeCode;
        this.transClazz = transClazz;
    }

    public Class getTransClazz() {
        return transClazz;
    }

    public static ReplyType fromCode(int typeCode){
        return getTransactionType(requestType -> (requestType.typeCode == typeCode));
    }

    private static ReplyType getTransactionType(Predicate<ReplyType> predicate){
        ReplyType[] values = values();
        for (ReplyType operationType : values) {
            if(predicate.test(operationType)){
                return operationType;
            }
        }

        throw new AssertionError("不支持的typeCode，");
    }

    public static boolean isProtocolCode(int typeCode) {
        ReplyType[] values = values();
        for (ReplyType transactionType : values) {
            if(transactionType.typeCode == typeCode){
                return true;
            }
        }
        return false;
    }
}
