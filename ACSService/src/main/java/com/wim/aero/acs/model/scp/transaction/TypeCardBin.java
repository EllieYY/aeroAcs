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
        int length = 2 * ((bit_array.length() + 1) / 2);
        int srcLength = (bit_count + 3) / 4;
        int targetLength = srcLength < length ? srcLength : length;

        String hexCardNo = bit_array.substring(0, targetLength);

//        System.out.println(hexCardNo);

        long put_Vlue = Long.parseLong(hexCardNo, 16);
        return String.valueOf(put_Vlue);
    }
}
