package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;

import java.util.Date;

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
 * //			7 - Card Database cleared due to SRAM buffer overflow
 **/
@Data
public class TypeSys extends TransactionBody {
    private int	error_code;					// non-zero indicates an error unless it's tran_code == 1 then it's power up diagnostics
                                            // Power Up diagnostics are interpreted as follows:
                                            // bit 0 = Loss of lock
                                            // bit 1 = Loss of clock
                                            // bit 2 = External Reset
                                            // bit 3 = Power on Clock
                                            // bit 4 = Watchdog Timer
                                            // bit 5 = Software
                                            // bit 6 = Low Voltage
                                            // bit 7 = Fault (Software Fault)
    private int	current_primary_comm;		// 0 == off-line, 1 == active, 2 == standby
    private int	previous_primary_comm;		// 0 == off-line, 1 == active, 2 == standby
    private int current_alternate_comm;		// Not used
    private int	previous_alternate_comm;	// Not used
    

    @Override
    public void process(QueueProducer queueProducer, SCPReplyTransaction transaction) {
        // TODO:
        int tranCode = transaction.getTranCode();
        if (error_code != 0) {
            if (tranCode == 1) {
                // SCP power-up diagnostics
            }
        }
    }
}
