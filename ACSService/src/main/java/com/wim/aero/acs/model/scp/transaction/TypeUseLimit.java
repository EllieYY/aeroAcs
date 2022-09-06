package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.AccessMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;

import java.util.Date;

/**
 * @title: TypeUseLimit
 * @author: Ellie
 * @date: 2022/04/01 15:36
 * @description:
 *
 * transaction codes for tranUseLimit:
 * 1 - use limit changed, reporting new limit
 **/
@Data
public class TypeUseLimit extends TransactionBody implements AccessEvent {
    private int ueeCount;				// the updated use count as a result of this access
    private long cardholderId;			// cardholder ID number

    @Override
    public void process(QueueProducer queueProducer, SCPReplyTransaction transaction) {
        String cardHolder = String.valueOf(cardholderId);
        int scpId = transaction.getScpId();
        long date = transaction.getTime() * 1000;
        long index = transaction.getSerNum();
        int sourceType = transaction.getSourceType();
        int sourceNum = transaction.getSourceNumber();
        int tranType = transaction.getTranType();
        int tranCode = transaction.getTranCode();

//        queueProducer.sendAccessMessage(
//                new AccessMessage(index, date, scpId, sourceType, sourceNum, tranType, tranCode, cardHolder,
//                        Constants.TRAN_TABLE_SRC_ACR, this.toString())
//        );
    }

    @Override
    public String getCardHolder() {
        return String.valueOf(cardholderId);
    }
}
