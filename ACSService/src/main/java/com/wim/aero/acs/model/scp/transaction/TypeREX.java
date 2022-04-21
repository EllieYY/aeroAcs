package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.model.mq.AccessMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: TypeREX
 * @author: Ellie
 * @date: 2022/04/01 16:05
 * @description: 开门按钮
 *
 * transaction codes for tranTypeREX
 * 1 - exit cycle: door use not verified
 * 2 - exit cycle: door not used
 * 3 - exit cycle: door used
 * 4 - host initiated request: door use not verified
 * 5 - host initiated request: door not used
 * 6 - host initiated request: door used
 * 7 - exit request denied: Airlock Busy
 * 8 - host request: Cannot complete due to Airlock Busy.
 * 9 - exit cycle: started
 **/
@Data
public class TypeREX extends TransactionBody {
    private int rex_number;				// rex that initiated the request (0 or 1)

    @Override
    public void process(QueueProducer queueProducer, SCPReplyTransaction transaction) {
        Map<Integer, String> des = new HashMap<>();
        des.put(1, "exit cycle: door use not verified");
        des.put(2, "exit cycle: door not used");
        des.put(3, "exit cycle: door used");
        des.put(4, "host initiated request: door use not verified");
        des.put(5, "host initiated request: door not used");
        des.put(6, "host initiated request: door used");
        des.put(7, "exit request denied: Airlock Busy");
        des.put(8, "host request: Cannot complete due to Airlock Busy.");
        des.put(9, "exit cycle: started");


        int scpId = transaction.getScpId();
        long date = transaction.getTime() * 1000;
        long index = transaction.getSerNum();
        int sourceType = transaction.getSourceType();
        int sourceNum = transaction.getSourceNumber();
        int tranType = transaction.getTranType();
        int tranCode = transaction.getTranCode();

        // 按钮的描述
        String cardHolder = "REX " + rex_number + " - " + des.get(tranCode);

        queueProducer.sendAccessMessage(
                new AccessMessage(index, date, scpId, sourceType, sourceNum, tranType, tranCode, cardHolder, transaction.toString())
        );

    }
}
