package com.wim.aero.acs.model.scp.reply;

import java.util.function.Predicate;

/**
 * @title: OperationType
 * @author: Ellie
 * @date: 2022/02/10 14:03
 * @description:
 **/
public enum ReplyType {
    REPLY_COMM_STATUS(0x02, SCPReplyCommStatus.class),
    REPLY_NAK(0x03, SCPReplyNAK.class),
    REPLY_ID_REPORT(0x04, SCPReplyIDReport.class),

    REPLY_TRAN_STATUS(0x06, SCPReplyTranStatus.class),

    REPLY_MSP1(0x08, SCPReplySrMsp1Drvr.class),
    REPLY_SIO(0x09, SCPReplySrSio.class),
    REPLY_MP(0x0A, SCPReplySrMp.class),
    REPLY_CP(0x0B, SCPReplySrCp.class),
    REPLY_ACR(0x0C, SCPReplySrAcr.class),
    REPLY_TZ(0x0C, SCPReplySrTz.class),
    REPLY_TV(0x0D, SCPReplySrTv.class),
    REPLY_COMND_STATUS(0x0F, SCPReplyCmndStatus.class),
    REPLY_MPG(0x10, SCPReplySrMpg.class),
    REPLY_AREA(0x11, SCPReplySrArea.class);

    public int getTypeCode() {
        return typeCode;
    }

    private int typeCode;
    private Class<? extends ReplyBody> replyClazz;

    ReplyType(int typeCode, Class<? extends ReplyBody> replyClazz) {
        this.typeCode = typeCode;
        this.replyClazz = replyClazz;
    }

    public Class getTransClazz() {
        return replyClazz;
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
