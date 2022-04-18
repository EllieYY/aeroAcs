package com.wim.aero.acs.model.scp.reply;

import java.util.function.Predicate;

/**
 * @title: OperationType
 * @author: Ellie
 * @date: 2022/02/10 14:03
 * @description:
 **/
public enum ReplyType {
    REPLY_COMM_STATUS(EnScpReplyType.enSCPReplyCommStatus, SCPReplyCommStatus.class),
    REPLY_NAK(EnScpReplyType.enSCPReplyNAK, SCPReplyNAK.class),
    REPLY_ID_REPORT(EnScpReplyType.enSCPReplyIDReport, SCPReplyIDReport.class),

    REPLY_TRAN_STATUS(EnScpReplyType.enSCPReplyTranStatus, SCPReplyTranStatus.class),

    REPLY_MSP1(EnScpReplyType.enSCPReplySrMsp1Drvr, SCPReplySrMsp1Drvr.class),
    REPLY_SIO(EnScpReplyType.enSCPReplySrSio, SCPReplySrSio.class),
    REPLY_MP(EnScpReplyType.enSCPReplySrMp, SCPReplySrMp.class),
    REPLY_CP(EnScpReplyType.enSCPReplySrCp, SCPReplySrCp.class),
    REPLY_ACR(EnScpReplyType.enSCPReplySrAcr, SCPReplySrAcr.class),
    REPLY_TZ(EnScpReplyType.enSCPReplySrTz, SCPReplySrTz.class),
    REPLY_TV(EnScpReplyType.enSCPReplySrTv, SCPReplySrTv.class),
    REPLY_COMND_STATUS(EnScpReplyType.enSCPReplyCmndStatus, SCPReplyCmndStatus.class),
    REPLY_MPG(EnScpReplyType.enSCPReplySrMpg, SCPReplySrMpg.class),
    REPLY_AREA(EnScpReplyType.enSCPReplySrArea, SCPReplySrArea.class);

    public EnScpReplyType getTypeCode() {
        return typeCode;
    }

    private EnScpReplyType typeCode;
    private Class<? extends ReplyBody> replyClazz;

    ReplyType(EnScpReplyType typeCode, Class<? extends ReplyBody> replyClazz) {
        this.typeCode = typeCode;
        this.replyClazz = replyClazz;
    }

    public Class getTransClazz() {
        return replyClazz;
    }

    public static ReplyType fromCode(EnScpReplyType typeCode){
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

    public static boolean isProtocolCode(EnScpReplyType typeCode) {
        ReplyType[] values = values();
        for (ReplyType transactionType : values) {
            if(transactionType.typeCode == typeCode){
                return true;
            }
        }
        return false;
    }
}
