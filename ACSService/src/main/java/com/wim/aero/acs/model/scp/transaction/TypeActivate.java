package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;

import java.util.Date;

/**
 * @title: TypeActivate
 * @author: Ellie
 * @date: 2022/04/01 15:55
 * @description:
 * transaction codes for tranTypeCoS:
 * 1 - disconnected (from an input point ID)
 * 2 - unknown (off-line): no report from the ID
 * 3 - secure (or de-activate relay)
 * 4 - alarm (or activated relay: perm or temp)
 * 5 - fault
 * 6 - exit delay in progress
 * 7 - entry delay in progress
 **/
@Data
public class TypeActivate extends TransactionBody {
    @Override
    public void process(QueueProducer queueProducer, SCPReplyTransaction transaction) {
    }
//    private int activationCount;   // 没有字段返回
}
