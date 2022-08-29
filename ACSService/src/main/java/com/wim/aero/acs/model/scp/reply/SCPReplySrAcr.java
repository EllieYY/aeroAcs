package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.AlarmMessage;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.model.scp.transaction.AlarmEvent;
import com.wim.aero.acs.service.QueueProducer;
import com.wim.aero.acs.service.ScpMessageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @title: SCPReplySrAcr
 * @author: Ellie
 * @date: 2022/04/13 11:34
 * @description:
 **/
@Data
@Slf4j
public class SCPReplySrAcr extends ReplyBody {
    private int number;				// ACR number
    private int mode;					// access control mode: C_308 encoded
    private int rdr_status;			// reader tamper  (TypeCoS::status)
    private int strk_status;			// strike relay   (TypeCoS::status)
    private int door_status;			// door status map  (TypeCoSDoor::door_status)
    private int ap_status;			// access point status (TypeCoSDoor::ap_status)
    private int rex_status0;			// rex-0 contact  (TypeCoS::status)
    private int rex_status1;			// rex-1 contact  (TypeCoS::status)
    private int led_mode;				// reader led mode:  C_315 encoded
    private int actl_flags;			// acr config flags (CC_ACR::actl_flags)
    private int altrdr_status;		// alternate reader tamper  (TypeCoS::status)
    private int actl_flags_extd;		// extended flags (same as CC_ACR::spare)
    private int nExtFeatureType;			// Ext. Feature Type (0=None, 1=Classroom, 2=Office, 3=Privacy, 4=Apartment, ..)
    private int nHardwareType;			// Hardware Type in use.
    private String  nExtFeatureStatus;	// Features variable by type, first byte hardware-specific binary inputs by convention.
    private long  nAuthModFlags;

    @Override
    public void process(QueueProducer queueProducer, int scpId) {
//        int deviceStatus = ScpMessageService.getPointStatus(door_status);
//        int tranCode = ScpMessageService.getTranCodeByCosState(door_status);
//
//        AlarmMessage sMessage = new AlarmMessage(
//                -1, System.currentTimeMillis(), scpId,
//                Constants.tranSrcACR, number, 0x09, tranCode, deviceStatus,
//                Constants.TRAN_TABLE_SRC_ACR,
//                this.toString(),
//                door_status);
//        queueProducer.sendStatusMessage(sMessage);
    }

}
