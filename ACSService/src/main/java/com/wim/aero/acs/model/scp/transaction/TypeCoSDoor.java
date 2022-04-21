package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.StatusMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;

import java.util.Date;

/**
 * @title: TypeCoSDoor
 * @author: Ellie
 * @date: 2022/04/01 16:02
 * @description:
 *
 * transaction codes for tranTypeCoSDoor:
 * 1 - disconnected
 * 2 - unknown (_RS bits: last known status)
 * 3 - secure
 * 4 - alarm (forced, held, or both)
 * 5 - fault (fault type is encoded in door_status byte
 **/
@Data
public class TypeCoSDoor extends TransactionBody {
    // status code byte encoding:
    //		0x07 - status mask: 0=inactive, 1=active, 2-7=supervisory fault codes:
    //				2==ground, 3==short, 4==open, 5==foreign voltage, 6==non-settling
    //		0x08 - off-line: comm to the input point is not valid
    //		0x10 - mask flag: set if the Monitor Point is MASK-ed
    //		0x20 - local mask flag: entry or exit delay in progress
    //		0x40 - entry delay in progress
    //		0x80 - not attached (the Monitor Point is not linked to an Input)
    private byte door_status;			// door status map

    // ap_status byte encoding
    //		0x01 - flag: set if a/p is unlocked
    //		0x02 - flag: access (exit) cycle in progress
    //		0x04 - flag: forced open status
    //		0x08 - flag: forced open mask status
    //		0x10 - flag: held open status
    //		0x20 - flag: held open mask status
    //		0x40 - flag: held open pre-alarm condition
    //		0x80 - flag: door is in "extended held open" mode
    private byte ap_status;				// supplemental access point status

    private byte ap_prior;				// previous ap status
    private byte door_prior;			// previous door status map

    @Override
    public void process(QueueProducer queueProducer, SCPReplyTransaction transaction) {
        int scpId = transaction.getScpId();
        long date = transaction.getTime() * 1000;
        long index = transaction.getSerNum();
        int sourceType = transaction.getSourceType();
        int sourceNum = transaction.getSourceNumber();
        int tranType = transaction.getTranType();
        int tranCode = transaction.getTranCode();

        queueProducer.sendStatusMessage(
                new StatusMessage(index, date, scpId, sourceType, sourceNum, tranType, tranCode, door_status, this.toString()));
    }

    int parseState(int tranCode, int commState) {
        if (tranCode == Constants.COS_TRAN_Disconnected) {

        } else if (tranCode == Constants.COS_TRAN_Unknown) {

        } else if (tranCode == Constants.COS_TRAN_Secure) {

        } else if (tranCode == Constants.COS_TRAN_Alarm) {

        } else if (tranCode == Constants.COS_TRAN_Fault) {

        } else if (tranCode == Constants.COS_TRAN_Exit) {

        } else if (tranCode == Constants.COS_TRAN_Entry) {

        }

        return 0;
    }
}
