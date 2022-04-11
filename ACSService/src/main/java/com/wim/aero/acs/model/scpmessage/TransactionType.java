package com.wim.aero.acs.model.scpmessage;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.protocol.accessLevel.*;
import com.wim.aero.acs.protocol.card.*;
import com.wim.aero.acs.protocol.device.*;

import java.util.function.Predicate;

/**
 * @title: OperationType
 * @author: Ellie
 * @date: 2022/02/10 14:03
 * @description:
 **/
public enum TransactionType {

    SCP_LCL_COS(0x02, 0x07, TypeCoS.class),
    SIO_COMM_COS(0x04, 0x02, TypeSioComm.class),
    SIO_TMPR_COS(0x05, 0x07, TypeCoS.class),
    SIO_PWR_COS(0x06, 0x07, TypeCoS.class),
    SIO_MP_COS(0x07, 0x07, TypeCoS.class),
    SIO_CP_COS(0x08, 0x07, TypeCoS.class),

    ACR_CARD_FULL(0x09, 0x05, TypeCardFull.class),
    ACR_DBL_CARD_FULL(0x09, 0x15, TypeCardFull.class),
    ACR_I64_CARD_FULL(0x09, 0x25, TypeCardFull.class),
    ACR_I64_CARD_FULL_IC32(0x09, 0x35, TypeCardFull.class),

    ACR_CARD_ID(0x09, 0x06, TypeCardID.class),
    ACR_DBL_CARD_ID(0x09, 0x16, TypeCardID.class),
    ACR_I64_CARD_ID(0x09, 0x26, TypeCardID.class),

    ACR_REX(0x09, 0x08, TypeREX.class),
    ACR_USER_CMD(0x09, 0x0B, TypeUserCmnd.class),
    ACR_MODE(0x09, 0x0D, TypeAcr.class),
    ACR_USE_LIMIT(0x09, 0x13, TypeUseLimit.class),

    ACR_COS_ELE(0x09, 0x1A, TypeCoSElevator.class),
    ACR_COS_ELE_ACS(0x09, 0x1D, TypeCoSElevatorAccess.class),


    ACR_TMPR_COS(0x0A, 0x07, TypeCoS.class),
    ACR_DOOR_COS(0x0B, 0x09, TypeCoSDoor.class),

    ACR_REX0_COS(0x0D, 0x07, TypeCoS.class),
    ACR_REX1_COS(0x0E, 0x07, TypeCoS.class),

    TIME_ZONE_ACT(0x0F, 0x0C, TypeActivate.class),
    TRIGGER_ACT(0x11, 0x0C, TypeActivate.class),
    TRIGGER_VAR_ACT(0x12, 0x0C, TypeActivate.class),

    MPG(0x13, 0x0E, TypeMPG.class),
    AREA(0x14, 0x0F, TypeArea.class),
    ACR_ALT_TMPR_COS(0x15, 0x07, TypeCoS.class);

    private int srcCode;
    private int tranCode;
    private Class<? extends TransactionBody> transClazz;

    TransactionType(int srcCode, int tranCode, Class<? extends TransactionBody> transClazz) {
        this.srcCode = srcCode;
        this.tranCode = tranCode;
        this.transClazz = transClazz;
    }

    public int getTranCode(){
        return tranCode;
    }

    public Class getTransClazz() {
        return transClazz;
    }

    public static TransactionType fromCode(int srcCode, int tranCode){
        return getTransactionType(requestType -> (requestType.tranCode == tranCode && requestType.srcCode == srcCode));
    }

    private static TransactionType getTransactionType(Predicate<TransactionType> predicate){
        TransactionType[] values = values();
        for (TransactionType operationType : values) {
            if(predicate.test(operationType)){
                return operationType;
            }
        }

        throw new AssertionError("不支持的sourceType和tranType");
    }

    public static boolean isProtocolCode(int srcCode, int tranCode) {
        TransactionType[] values = values();
        for (TransactionType transactionType : values) {
            if(transactionType.tranCode == tranCode && transactionType.srcCode == srcCode){
                return true;
            }
        }
        return false;
    }
}
