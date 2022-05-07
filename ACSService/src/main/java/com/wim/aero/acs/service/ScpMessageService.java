package com.wim.aero.acs.service;

import com.wim.aero.acs.db.entity.EEventRecord;
import com.wim.aero.acs.db.service.impl.EEventRecordServiceImpl;
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

    private final QueueProducer queueProducer;
    private final EEventRecordServiceImpl eventRecordService;
    private final ScpCenter scpCenter;

    @Autowired
    public ScpMessageService(QueueProducer queueProducer, EEventRecordServiceImpl eventRecordService, ScpCenter scpCenter) {
        this.queueProducer = queueProducer;
        this.eventRecordService = eventRecordService;
        this.scpCenter = scpCenter;
    }


    public void dealTransaction(SCPReplyTransaction transaction) {
        int scpId = transaction.getScpId();
        int sourceType = transaction.getSourceType();
        int tranType = transaction.getTranType();
        long eventNo = transaction.getSerNum();

        if (scpCenter.needIntercept(scpId, eventNo)) {
            // 过滤掉不处理
            return;
        }

        // 事务信息入总库
        saveEventInfo(transaction);

        if (!TransactionType.isProtocolCode(sourceType, tranType)) {
            log.info("不支持的SCPReplyTransaction类型 - {}", transaction.toString());
            return;
        }

        log.info(transaction.toString());

        // 类型转换
        Class<TransactionBody> bodyClazz = TransactionType.fromCode(sourceType, tranType).getTransClazz();
        TransactionBody body = JsonUtil.fromJson(transaction.getArgJsonStr(), bodyClazz);

        body.process(queueProducer, transaction);
    }

    private void saveEventInfo(SCPReplyTransaction transaction) {
        int scpId = transaction.getScpId();
        Date date = new Date(transaction.getTime() * 1000);
        long index = transaction.getSerNum();
        int sourceType = transaction.getSourceType();
        int sourceNum = transaction.getSourceNumber();
        int tranType = transaction.getTranType();
        int tranCode = transaction.getTranCode();

        EEventRecord record = new EEventRecord();
        record.setControllerId(scpId);
        record.setEventIndex(index);
        record.setEventsTime(date);
        record.setEventSourceType(sourceType);
        record.setSourceCode(sourceNum);
        record.setEventType(tranType);
        record.setEventTypeCode(tranCode);

        eventRecordService.save(record);
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

        log.info(reply.toString());

        // 类型转换
        Class<ReplyBody> bodyClazz = ReplyType.fromCode(enScpReplyType).getTransClazz();
        ReplyBody body = JsonUtil.fromJson(reply.getContent(), bodyClazz);

        // 业务处理
        int scpId = reply.getScpId();
        body.process(queueProducer, scpId);
    }

}
