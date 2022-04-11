package com.wim.aero.acs.service;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.db.entity.EAccessRecord;
import com.wim.aero.acs.db.entity.EAlarmRecord;
import com.wim.aero.acs.db.entity.ELogRecord;
import com.wim.aero.acs.db.service.impl.EAccessRecordServiceImpl;
import com.wim.aero.acs.db.service.impl.EAlarmRecordServiceImpl;
import com.wim.aero.acs.db.service.impl.ELogRecordServiceImpl;
import com.wim.aero.acs.model.scpmessage.SCPReplyTransaction;
import com.wim.aero.acs.model.scpmessage.AccessEvent;
import com.wim.aero.acs.model.scpmessage.TransactionBody;
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
public class TransactionService {
    private final EAccessRecordServiceImpl accessRecordService;
    private final EAlarmRecordServiceImpl alarmRecordService;
    private final ELogRecordServiceImpl logRecordService;

    @Autowired
    public TransactionService(EAccessRecordServiceImpl accessRecordService,
                              EAlarmRecordServiceImpl alarmRecordService,
                              ELogRecordServiceImpl logRecordService) {
        this.accessRecordService = accessRecordService;
        this.alarmRecordService = alarmRecordService;
        this.logRecordService = logRecordService;
    }

    public void dealTransaction(SCPReplyTransaction transaction) {
        transaction.updateTransactionBody();

        int scpId = transaction.getScpId();
        Date date = transaction.getTime();
        long index = transaction.getSerNum();
        int sourceType = transaction.getSourceType();
        int sourceNum = transaction.getSourceNumber();
        int tranType = transaction.getTranType();
        int tranCode = transaction.getTranCode();

        // 访问事件
        if (sourceType == Constants.tranSrcACR && (
            tranType == Constants.tranTypeCardFull ||
            tranType == Constants.tranTypeDblCardFull ||
            tranType == Constants.tranTypeI64CardFull ||
            tranType == Constants.tranTypeI64CardFullIc32 ||
            tranType == Constants.tranTypeCardID ||
            tranType == Constants.tranTypeDblCardID ||
            tranType == Constants.tranTypeI64CardID)) {

            AccessEvent accessEvent = (AccessEvent) transaction.updateTransactionBody();
            String cardHolder = accessEvent.getCardHolder();

            EAccessRecord record = new EAccessRecord(
                    index, date, scpId, sourceType, sourceNum, tranType, tranCode, cardHolder, accessEvent.toString());

            log.info(record.toString());
            accessRecordService.save(record);

        } else if (sourceType == Constants.tranSrcMP) {   // 告警事件
            TransactionBody body = transaction.getBody();
            EAlarmRecord record = new EAlarmRecord(index, date, scpId, sourceType, sourceNum, tranType, tranCode, body.toString());

            log.info(record.toString());
            alarmRecordService.save(record);
        } else { // 日志事件
            TransactionBody body = transaction.getBody();
            ELogRecord record = new ELogRecord(index, date, scpId, sourceType, sourceNum, tranType, tranCode, body.toString());
            log.info(record.toString());
            logRecordService.save(record);
        }

    }

}
