package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.AlarmMessage;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.model.mq.StatusMessage;
import com.wim.aero.acs.model.scp.transaction.AlarmEvent;
import com.wim.aero.acs.service.QueueProducer;
import com.wim.aero.acs.service.ScpMessageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

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
        // TODO： 状态解析
        Map<Integer, Integer> cosStateCodeMap = new HashMap<>();
        cosStateCodeMap.put(0x00, Constants.TRAGET_STATE_CLOSE);
        cosStateCodeMap.put(0x01, Constants.TRAGET_STATE_OPEN);
        cosStateCodeMap.put(0x02, Constants.TRAGET_STATE_FAULT);
        cosStateCodeMap.put(0x03, Constants.TRAGET_STATE_FAULT);
        cosStateCodeMap.put(0x04, Constants.TRAGET_STATE_OPEN);
        cosStateCodeMap.put(0x05, Constants.TRAGET_STATE_OPEN);
        cosStateCodeMap.put(0x06, Constants.TRAGET_STATE_FAULT);
        cosStateCodeMap.put(0x07, Constants.TRAGET_STATE_FAULT);
        cosStateCodeMap.put(0x08, Constants.TRAGET_STATE_INVALID);
        cosStateCodeMap.put(0x10, Constants.TRAGET_STATE_CLOSE);
        cosStateCodeMap.put(0x20, Constants.TRAGET_STATE_OPEN);
        cosStateCodeMap.put(0x40, Constants.TRAGET_STATE_OPEN);
        cosStateCodeMap.put(0x80, Constants.TRAGET_STATE_CLOSE);

        int deviceStatus = cosStateCodeMap.get(door_status);
//        int tranCode = ScpMessageService.getTranCodeByCosState(door_status);

        // 状态事件
        StatusMessage sMessage = new StatusMessage(
                -1, System.currentTimeMillis(), scpId,
                Constants.tranSrcACR, number, 0x09, 0, deviceStatus,
                Constants.TRAN_TABLE_SRC_ACR,
                this.toString());
        queueProducer.sendStatusMessage(sMessage);


        //TODO:报警事件
        if (ap_status == 4 || ap_status == 16 || ap_status == 20) {
            AlarmMessage alarmMessage = new AlarmMessage(
                    -1, System.currentTimeMillis(), scpId,
                    Constants.tranSrcACR, number, 0x09, 4,
                    Constants.TRAN_TABLE_SRC_ACR,
                    ap_status,
                    this.toString());
            queueProducer.sendAlarmMessage(alarmMessage);
        }

    }

}
