package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.service.QueueProducer;

/**
 * @title: TransactionBody
 * @author: Ellie
 * @date: 2022/04/06 10:48
 * @description:
 **/
public abstract class ReplyBody {
    abstract public void process(QueueProducer queueProducer, int scpId);
}
