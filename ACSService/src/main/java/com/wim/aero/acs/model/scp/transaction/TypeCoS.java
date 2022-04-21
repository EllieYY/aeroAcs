package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.AlarmMessage;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.model.mq.StatusMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: TypeCoS
 * @author: Ellie
 * @date: 2022/04/01 15:05
 * @description: 0x07 change-of-state
 *
 * transaction codes for tranTypeCoSDoor:
 * 1 - disconnected
 * 2 - unknown (_RS bits: last known status)
 * 3 - secure
 * 4 - alarm (forced, held, or both)
 * 5 - fault (fault type is encoded in door_status byte
 * 6 - Exit delay in progress
 * 7 - Entry delay in progress
 **/
@Data
public class TypeCoS extends TransactionBody {
    // status code byte encoding:
    //		0x07 - status mask: 0=inactive, 1=active, 2-7=supervisory fault codes:
    //				2==ground, 3==short, 4==open, 5==foreign voltage, 6==non-settling
    //		0x08 - off-line: comm to the input point is not valid
    //		0x10 - mask flag: set if the Monitor Point is MASK-ed
    //		0x20 - local mask flag: entry or exit delay in progress
    //		0x40 - entry delay in progress
    //		0x80 - not attached (the Monitor Point is not linked to an Input)
    private byte status;		 // new status
    private byte old_sts;		 // previous status (prior to this CoS report)

    @Override
    public void process(QueueProducer queueProducer, SCPReplyTransaction transaction) {
        int scpId = transaction.getScpId();
        long date = transaction.getTime() * 1000;
        long index = transaction.getSerNum();
        int sourceType = transaction.getSourceType();
        int sourceNum = transaction.getSourceNumber();
        int tranType = transaction.getTranType();
        int tranCode = transaction.getTranCode();

        if (tranType == Constants.tranTypeCoS && (
                sourceType == Constants.tranSrcMP || sourceType == Constants.tranSrcCP ||
                        sourceType == Constants.tranSrcSioCom)) {

            if (tranCode == Constants.COS_TRAN_Alarm) {   // 报警事件
                // 报警状态需要描述
                String des = EventDescriptionUtil.getTypeCosStatusDes(status) + " " + this.toString();
                queueProducer.sendAlarmMessage(
                    new AlarmMessage(index, date, scpId, sourceType, sourceNum, tranType, tranCode, des));
            }

            // 解析器
            Map<Integer, StatusParser> parserMap = new HashMap<>();
            parserMap.put(Constants.tranSrcMP, new MpStatusParser());
            parserMap.put(Constants.tranSrcCP, new CpStatusParser());
            parserMap.put(Constants.tranSrcAcrRex0, new CpStatusParser());
            parserMap.put(Constants.tranSrcAcrRex1, new CpStatusParser());
            parserMap.put(Constants.tranSrcAcrTmpr, new CpStatusParser());
            parserMap.put(Constants.tranSrcCP, new CpStatusParser());
            parserMap.put(Constants.tranSrcAcrDoor, new AcrStatusParser());

            if (parserMap.containsKey(sourceType)) {   // 记录状态变化
                StatusParser parser = parserMap.get(sourceType);
                // TODO:先记录，再解析
                int deviceStatus = parser.parseStatus(tranCode, this.status);

                queueProducer.sendStatusMessage(
                        new StatusMessage(index, date, scpId, sourceType, sourceNum, tranType, tranCode, status, this.toString()));

            } else {    // log
                queueProducer.sendLogMessage(
                        new LogMessage(index, date, scpId, sourceType, sourceNum, tranType, tranCode, this.toString()));
            }
        }
    }

    interface StatusParser {
        int parseStatus(int tranCode, int status);
    }

    static class MpStatusParser implements StatusParser {
        @Override
        public int parseStatus(int tranCode, int status) {
            // TODO:
            return 0;
        }
    }

    static class CpStatusParser implements StatusParser {
        @Override
        public int parseStatus(int tranCode, int status) {
            // TODO:
            return 0;
        }
    }

    static class AcrStatusParser implements StatusParser {
        @Override
        public int parseStatus(int tranCode, int status) {
            // TODO:不对，应该挪到门状态那边
            return 0;
        }
    }

}
