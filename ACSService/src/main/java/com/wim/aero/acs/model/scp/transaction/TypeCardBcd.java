package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.AccessMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @title: TypeCardBcd
 * @author: Ellie
 * @date: 2022/04/18 17:52
 * @description:
 * // transaction codes for tranTypeCardBcd:
 * //		1 - access denied, invalid card format, forward read
 * //		2 - access denied, invalid card format, reverse read
 **/
@Data
@Slf4j
public class TypeCardBcd extends TransactionBody {
    private int digit_count;			// number of valid digits (0-9 plus A-F)
    private String bcd_array;		// each entry holds a hex digit: 0x0 - 0xF


    @Override
    public void process(QueueProducer queueProducer,SCPReplyTransaction transaction) {
        String cardHolder = bcd_array.substring(0, digit_count);
        int scpId = transaction.getScpId();
        long date = transaction.getTime() * 1000;
        long index = transaction.getSerNum();
        int sourceType = transaction.getSourceType();
        int sourceNum = transaction.getSourceNumber();
        int tranType = transaction.getTranType();
        int tranCode = transaction.getTranCode();

        queueProducer.sendAccessMessage(
                new AccessMessage(index, date, scpId, sourceType, sourceNum, tranType, tranCode, cardHolder,
                        Constants.TRAN_TABLE_SRC_ACR, this.toString())
        );
    }
}
