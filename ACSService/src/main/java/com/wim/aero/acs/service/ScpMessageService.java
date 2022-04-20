package com.wim.aero.acs.service;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.db.entity.EAccessRecord;
import com.wim.aero.acs.db.entity.EAlarmRecord;
import com.wim.aero.acs.db.entity.ELogRecord;
import com.wim.aero.acs.db.service.impl.EAccessRecordServiceImpl;
import com.wim.aero.acs.db.service.impl.EAlarmRecordServiceImpl;
import com.wim.aero.acs.db.service.impl.ELogRecordServiceImpl;
import com.wim.aero.acs.model.mq.AccessMessage;
import com.wim.aero.acs.model.mq.AlarmMessage;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.model.scp.reply.EnScpReplyType;
import com.wim.aero.acs.model.scp.reply.ReplyBody;
import com.wim.aero.acs.model.scp.reply.ReplyType;
import com.wim.aero.acs.model.scp.reply.SCPReply;
import com.wim.aero.acs.model.scp.transaction.*;
import com.wim.aero.acs.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @title: TransactionService
 * @author: Ellie
 * @date: 2022/04/06 10:27
 * @description: transaction处理
 **/
@Slf4j
@Service
public class ScpMessageService {
    private final EAccessRecordServiceImpl accessRecordService;
    private final EAlarmRecordServiceImpl alarmRecordService;
    private final ELogRecordServiceImpl logRecordService;
    private final QueueProducer queueProducer;

    @Autowired
    public ScpMessageService(EAccessRecordServiceImpl accessRecordService,
                             EAlarmRecordServiceImpl alarmRecordService,
                             ELogRecordServiceImpl logRecordService, QueueProducer queueProducer) {
        this.accessRecordService = accessRecordService;
        this.alarmRecordService = alarmRecordService;
        this.logRecordService = logRecordService;
        this.queueProducer = queueProducer;
    }

    public void dealTransaction(SCPReplyTransaction transaction) {
        int scpId = transaction.getScpId();
        Date date = new Date(transaction.getTime() * 1000);
        long index = transaction.getSerNum();
        int sourceType = transaction.getSourceType();
        int sourceNum = transaction.getSourceNumber();
        int tranType = transaction.getTranType();
        int tranCode = transaction.getTranCode();

        if (!TransactionType.isProtocolCode(sourceType, tranType)) {
            log.info("不支持的SCPReplyTransaction类型 - srcType:{}, tranType:{}", sourceType, tranType);
            return ;
        }

        // 类型转换
        Class<TransactionBody> bodyClazz = TransactionType.fromCode(sourceType, tranType).getTransClazz();
        TransactionBody body = JsonUtil.fromJson(transaction.getArgJsonStr(), bodyClazz);

        // 访问事件
        if (sourceType == Constants.tranSrcACR && (
                tranType == Constants.tranTypeCardBin ||
                tranType == Constants.tranTypeCardBcd ||
                tranType == Constants.tranTypeCardFull ||
                tranType == Constants.tranTypeDblCardFull ||
                tranType == Constants.tranTypeI64CardFull ||
                tranType == Constants.tranTypeI64CardFullIc32 ||
                tranType == Constants.tranTypeCardID ||
                tranType == Constants.tranTypeDblCardID ||
                tranType == Constants.tranTypeI64CardID)) {

            AccessEvent accessEvent = (AccessEvent) body;
            String cardHolder = accessEvent.getCardHolder();

            EAccessRecord record = new EAccessRecord(
                    index, date, scpId, sourceType, sourceNum, tranType, tranCode, cardHolder, accessEvent.toString());

            queueProducer.sendAccessMessage(
                    new AccessMessage(
                            index, date, scpId, sourceType, sourceNum, tranType, tranCode, cardHolder,
                            accessEvent.toString()));

        } else if (tranType == Constants.tranTypeCoS && (
                sourceType == Constants.tranSrcMP ||
                        sourceType == Constants.tranSrcCP ||
                        sourceType == Constants.tranSrcSioCom)) {
            TypeCoS cos = (TypeCoS) body;
            if (tranCode == Constants.COS_Alarm) {    // 报警事件

            }

            EAlarmRecord record = new EAlarmRecord(index, date, scpId, sourceType, sourceNum, tranType, tranCode, body.toString());

//            log.info(record.toString());
//            alarmRecordService.save(record);

            queueProducer.sendAlarmMessage(
                    new AlarmMessage(
                            index, date, scpId, sourceType, sourceNum, tranType, tranCode, transaction.getArgJsonStr()));

        } else { // 日志事件
            ELogRecord record = new ELogRecord(index, date, scpId, sourceType, sourceNum, tranType, tranCode, body.toString());
//            log.info(record.toString());
//            logRecordService.save(record);

            queueProducer.sendLogMessage(
                    new LogMessage(
                            index, date, scpId, sourceType, sourceNum, tranType, tranCode, transaction.getArgJsonStr()));
        }
    }


    public void dealScpeply(SCPReply reply) {
        int type = reply.getType();
        EnScpReplyType enScpReplyType = EnScpReplyType.fromCode(type);
        if (enScpReplyType == EnScpReplyType.enSCPReplyUnknown) {
            log.info("不支持的ScpReply类型 {}", reply);
            return;
        }

        if (!ReplyType.isProtocolCode(enScpReplyType)) {
            log.info("不支持的业务类型 - {}", reply);
            return;
        }

        // TODO:test code
//        if (enScpReplyType != EnScpReplyType.enSCPReplyCmndStatus ||
//                enScpReplyType != EnScpReplyType.enSCPReplyCommStatus) {
//            return;
//        }

        // 类型转换
        Class<ReplyBody> bodyClazz = ReplyType.fromCode(enScpReplyType).getTransClazz();
        ReplyBody body = JsonUtil.fromJson(reply.getContent(), bodyClazz);

        // 业务处理
        int scpId = reply.getScpId();
        body.process(scpId);
    }

}
