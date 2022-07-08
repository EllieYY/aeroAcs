package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.AlarmMessage;
import com.wim.aero.acs.model.mq.StatusMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;

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

        // 状态事件
        int deviceStatus = Constants.TRAN_CODE_MAP.get(tranCode);
        int targetType = this.cosSrcMap.get(sourceType);

        queueProducer.sendStatusMessage(new AlarmMessage(index, date, scpId,
                        sourceType, sourceNum, tranType, tranCode, deviceStatus, targetType, this.toString(), this.status));

    }

    private static final Map<Integer, Integer> cosSrcMap = Map.of(
            0x02, Constants.TRAN_TABLE_SRC_MP,   // 控制器内部报警点 cos
            0x05, Constants.TRAN_TABLE_SRC_SIO,   // sio防撬 cos
            0x06, Constants.TRAN_TABLE_SRC_SIO,   // sio电源 cos
            0x07, Constants.TRAN_TABLE_SRC_MP,   // 报警点 cos
            0x08, Constants.TRAN_TABLE_SRC_CP,   // 控制点 cos
            0x0A, Constants.TRAN_TABLE_SRC_ACR,   // 读卡器防撬 cos
            0x0D, Constants.TRAN_TABLE_SRC_ACR,  // 按钮0 cos
            0x0E, Constants.TRAN_TABLE_SRC_ACR,  // 按钮1  cos
            0x15, Constants.TRAN_TABLE_SRC_ACR,  // 备用读卡器
            0x18, Constants.TRAN_TABLE_SRC_SCP  // scpweb
    );


}
