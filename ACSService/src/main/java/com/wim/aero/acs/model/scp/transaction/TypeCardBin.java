package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.AccessMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;

import java.util.Date;

/**
 * @title: TypeCardBin
 * @author: Ellie
 * @date: 2022/04/18 17:48
 * @description:
 *
 * // transaction codes for tranTypeCardBin:
 * //		1 - access denied, invalid card format
 * // 		2 - access granted, invalid card format
 **/
@Data
public class TypeCardBin extends TransactionBody implements AccessEvent {
    private int bit_count;			// number of valid data bits
    private String bit_array;		// first bit is (0x80 & bit_array[0])

    @Override
    public void process(QueueProducer queueProducer, SCPReplyTransaction transaction) {

//        String cardHolder = bit_array.substring(0, bit_count);
//
//        int scpId = transaction.getScpId();
//        long date = transaction.getTime() * 1000;
//        long index = transaction.getSerNum();
//        int sourceType = transaction.getSourceType();
//        int sourceNum = transaction.getSourceNumber();
//        int tranType = transaction.getTranType();
//        int tranCode = transaction.getTranCode();
//
//        queueProducer.sendAccessMessage(
//                new AccessMessage(index, date, scpId, sourceType, sourceNum, tranType, tranCode, cardHolder,
//                        Constants.TRAN_TABLE_SRC_ACR, this.toString())
//        );
    }

    @Override
    public String getCardHolder() {
        int length = bit_array.length();
        int targetLength = bit_count > length ? bit_count : length;
        return bit_array.substring(0, targetLength);
    }
}
