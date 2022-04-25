package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @title: SCPReplyIDReport
 * @author: Ellie
 * @date: 2022/04/13 11:31
 * @description: type = 4
 **/
@Data
@Slf4j
public class SCPReplyIDReport extends ReplyBody {
    private int  device_id;			// identification of the replying device (3=HID)
    private int  device_ver;			// hardware version: 27==X1100
    private int  sft_rev_major;		// software revision, major
    private int  sft_rev_minor;		// software revision, (minor * 10 + build)
    private long   serial_number;		// serial number
    private long   ram_size;			// amount of ram installed
    private long   ram_free;			// amount of ram available
    private long   e_sec;				// current clock
    private long   db_max;				// access database size
    private long   db_active;			// number of active records
    private byte   dip_switch_pwrup;	// DIP switch at power-up: diagnostic
    private byte   dip_switch_current;	// DIP switch current value: diagnostic
    private int  scp_id;				// the SCP's ID, as set by the host << new 02/03/97 <<
    private int  firmware_advisory;	// 0==no firmware action, 1==must reset first, 2==starting load
    private int  scp_in_1;			// Scp local monitor "IN 1" state
    private int  scp_in_2;			// Scp local monitor "IN 2" state
//    private long   adb_max;				// Not used
//    private long   adb_active;			// Not used
//    private long   bio1_max;			// Not used
//    private long   bio1_active;			// Not used
//    private long   bio2_max;			// Not used
//    private long   bio2_active;			// Not used
//    private int  nOemCode;			// Not used
    private byte   config_flags;        // Configuration flags.  (Bit-0 = Needs CC_SCP_SCP configuration, SCP not yet known to driver)
    private String mac_addr;			// MAC Address, if applicable, LSB first.
    private int   tls_status;			// TLS status
    private byte   oper_mode;			// Current Operating Mode
    private int  scp_in_3;			// Scp local monitor "IN 3" state
    private long   cumulative_bld_cnt;	// Cumulative build count

    @Override
    public void process(QueueProducer queueProducer, int scpId) {
        log.info(this.toString());
    }
}
