package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.service.QueueProducer;

import java.util.Date;

/**
 * @title: TransactionBody
 * @author: Ellie
 * @date: 2022/04/06 10:48
 * @description:
 **/
public abstract class TransactionBody {
    public abstract void process(QueueProducer queueProducer, SCPReplyTransaction transaction);
}
