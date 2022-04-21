package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @title: TypeUserCmnd
 * @author: Ellie
 * @date: 2022/04/01 15:57
 * @description: 用户输入密码or命令
 *
 * transaction codes for tranTypeUserCmnd:
 * 1 - command entered by the user...
 **/
@Data
public class TypeUserCmnd extends TransactionBody {
    private int nKeys;				// number of user command digits entered
    private List<String> keys;		// keys[16]; null terminated string: '0' through '9'

    @Override
    public void process(QueueProducer queueProducer, SCPReplyTransaction transaction) {

    }
}
