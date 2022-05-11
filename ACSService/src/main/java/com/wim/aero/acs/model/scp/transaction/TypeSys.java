package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.StatusMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;

/**
 * @title: TypeSys
 * @author: Ellie
 * @date: 2022/04/20 08:27
 * @description:
 *
 * // transaction codes for tranTypeSys:
 * //			1 - SCP power-up diagnostics
 * //			2 - host comm, off-line --> formats to TypeSytsComm
 * //			3 - host comm, on-line ---> formats to TypeSytsComm
 * //			4 - Transaction count exceeds the preset limit
 * //			5 - Autosave - Configuration save complete
 * //			6 - Autosave - Database Complete
 * //			7 - Card Database cleared due to SRAM buffer overflow  故障
 **/
@Data
public class TypeSys extends TransactionBody {
    private byte error_code;				// non-zero indicates an error unless it's tran_code == 1 then it's power up diagnostics
                                            // Power Up diagnostics are interpreted as follows:
                                            // bit 0 = Loss of lock
                                            // bit 1 = Loss of clock
                                            // bit 2 = External Reset
                                            // bit 3 = Power on Clock
                                            // bit 4 = Watchdog Timer    //TODO 故障
                                            // bit 5 = Software
                                            // bit 6 = Low Voltage       //TODO 故障
                                            // bit 7 = Fault (Software Fault)  //TODO 故障
    private int	current_primary_comm;		// 0 == off-line, 1 == active, 2 == standby
    private int	previous_primary_comm;		// 0 == off-line, 1 == active, 2 == standby
    private int current_alternate_comm;		// Not used
    private int	previous_alternate_comm;	// Not used
    

    @Override
    public void process(QueueProducer queueProducer, SCPReplyTransaction transaction) {
        int scpId = transaction.getScpId();
        long date = transaction.getTime() * 1000;
        long index = transaction.getSerNum();
        int sourceType = transaction.getSourceType();
        int sourceNum = transaction.getSourceNumber();
        int tranType = transaction.getTranType();
        int tranCode = transaction.getTranCode();

        // 状态通知和转换
        int targetState = getTragetState(tranCode);
        queueProducer.sendStatusMessage(
                new StatusMessage(index, date, scpId,
                        sourceType, sourceNum, tranType, tranCode,
                        targetState,
                        Constants.TRAN_TABLE_SRC_SCP,
                        this.toString()));
    }

    private int getTragetState(int tranCode) {
        int result = Constants.TRAGET_STATE_INVALID;
        if (tranCode == 1) {   // 上电诊断
            if ((error_code & 0xD0) > 0) {
                result = Constants.TRAGET_STATE_FAULT;    // 故障
            } else {
                result = Constants.TRAGET_STATE_VALID;
            }
        } else if (tranCode == 2) {
            result = Constants.TRAGET_STATE_INVALID;
            if (error_code != 0) {
                result = Constants.TRAGET_STATE_FAULT;
            }
        } else if (tranCode == 3) {
            result = Constants.TRAGET_STATE_VALID;
            if (error_code != 0) {
                result = Constants.TRAGET_STATE_FAULT;
            }
        } else if (tranCode == 4) {
            result = Constants.TRAGET_STATE_WARN;
        } else {
            result = Constants.TRAGET_STATE_VALID;
        }
        return result;
    }
}
