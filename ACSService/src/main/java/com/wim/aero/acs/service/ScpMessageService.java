package com.wim.aero.acs.service;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.db.entity.EAccessRecord;
import com.wim.aero.acs.db.entity.EAlarmRecord;
import com.wim.aero.acs.db.entity.ELogRecord;
import com.wim.aero.acs.db.service.impl.EAccessRecordServiceImpl;
import com.wim.aero.acs.db.service.impl.EAlarmRecordServiceImpl;
import com.wim.aero.acs.db.service.impl.ELogRecordServiceImpl;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.model.scp.reply.ReplyBody;
import com.wim.aero.acs.model.scp.reply.ReplyType;
import com.wim.aero.acs.model.scp.reply.SCPReply;
import com.wim.aero.acs.model.scp.transaction.SCPReplyTransaction;
import com.wim.aero.acs.model.scp.transaction.AccessEvent;
import com.wim.aero.acs.model.scp.transaction.TransactionBody;
import com.wim.aero.acs.model.scp.transaction.TransactionType;
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
        Date date = transaction.getTime();
        long index = transaction.getSerNum();
        int sourceType = transaction.getSourceType();
        int sourceNum = transaction.getSourceNumber();
        int tranType = transaction.getTranType();
        int tranCode = transaction.getTranCode();

        //TODO:mq test
        queueProducer.sendLogMessage(
                new LogMessage(
                        index, date, scpId, sourceType, sourceNum, tranType, tranCode, transaction.getArgJsonStr()));

        if (!TransactionType.isProtocolCode(sourceType, tranType)) {
            log.info("不支持的SCPReplyTransaction类型 - srcType:{}, tranType:{}", sourceType, tranType);
            return ;
        }

        // 类型转换
        Class<TransactionBody> bodyClazz = TransactionType.fromCode(sourceType, tranType).getTransClazz();
        TransactionBody body = JsonUtil.fromJson(transaction.getArgJsonStr(), bodyClazz);



        // 访问事件
        if (sourceType == Constants.tranSrcACR && (
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

            log.info(record.toString());
            accessRecordService.save(record);

        } else if (sourceType == Constants.tranSrcMP) {   // 告警事件
            EAlarmRecord record = new EAlarmRecord(index, date, scpId, sourceType, sourceNum, tranType, tranCode, body.toString());

            log.info(record.toString());
            alarmRecordService.save(record);
        } else { // 日志事件
            ELogRecord record = new ELogRecord(index, date, scpId, sourceType, sourceNum, tranType, tranCode, body.toString());
            log.info(record.toString());
            logRecordService.save(record);
        }

    }


    public void dealScpeply(SCPReply reply) {
        int type = reply.getType();
        if (!ReplyType.isProtocolCode(type)) {
            log.info("不支持的ScpReply类型 - {}", type);
            return ;
        }

        Class<ReplyBody> bodyClazz = ReplyType.fromCode(type).getTransClazz();
        ReplyBody body = JsonUtil.fromJson(reply.getContent(), bodyClazz);

        // TODO：更细致的业务处理


        log.info(body.toString());

    }

}
