package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.model.mq.StatusMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @title: SCPReplySrSio
 * @author: Ellie
 * @date: 2022/04/13 11:33
 * @description:
 **/
@Data
@Slf4j
public class SCPReplySrSio extends ReplyBody {
    private int number;				// SIO number
    private int com_status;			// comm status: encoded per tran codes for tranTypeSioComm
                                    //  	1	- comm disabled (result of host command)
                                    //  	2	- off-line: timeout (no/bad response from unit)
                                    //  	3	- off-line: invalid identification from SIO
                                    //  	4	- off-line: Encryption could not be established
                                    //  	5	- on-line: normal connection
                                    //		6   - hexLoad report: ser_num is address loaded (-1 == last record)

    private int msp1_dnum;			// MSP1 driver number (0, 1, ...)
                                    // the following block is valid only if the SIO is on-line
    private long  com_retries;			// retries since power-up, cumulative
    private int ct_stat;				// cabinet tamper status: TranCoS::status encoded
    private int pw_stat;				// power monitor status: TranCoS::status encoded
    private int model;				// identification: see C03_02
    private int revision;				// firmware revision number: see C03_02
    long  serial_number;		// serial number
    private int inputs;				// number of inputs
    private int outputs;				// number of outputs
    private int readers;				// number of readers
    private List<Integer> ip_stat;			// 32 input point status: TranCoS::status encoded
    private List<Integer> op_stat;			// 16 output point status: TranCoS::status encoded
    private List<Integer> rdr_stat;			// 8 reader tamper status: TranCoS::status encoded

    // extended Sio ID info --- fields added 2006/05/15
    private int nExtendedInfoValid;	// use the data below only if this field is non-zero
    private int nHardwareId;			// MR-50 == , MR-52 == , MR-16In == , MR-16Out == , MR-DT == ,
    private int nHardwareRev;
    private int nProductId;			// same as model
    private int nProductVer;			// -- application specific version of this ProductId
    private int nFirmwareBoot;		// BOOT code version info:   (maj(4) << 12)+(min(8) << 4) + (bld(4))
    private int nFirmwareLdr;			// Loader code version info: (maj(4) << 12)+(min(8) << 4) + (bld(4))
    private int nFirmwareApp;			// App code version info:    (maj(4) << 12)+(min(8) << 4) + (bld(4))
    private int nOemCode;				// OEM code assigned to this SIO (0 == none)
    private byte  nEncConfig;			// SIO comm encryption support: 0=None, 1=AES Default Key, 2=AES Master/Secret Key, 3= PKI, 6=AES256 Session key
    private byte  nEncKeyStatus;		// Status of Master/Secret Key; 0=Not Loaded to EP, 1=Loaded, unverified, 2=Loaded, conflicts w/SIO, 3=Loaded, Verified, 4=AES256 Verified.
    private List<Integer>  mac_addr;			// 6 MAC Address, if applicable, LSB first.
    private int emg_stat;				// emergency switch status: TranCoS::status encoded


    @Override
    public void process(QueueProducer queueProducer, int scpId) {
        log.info("[sio状态] - scpId[{}], sio[{}], msp1dNum[{}], comStatus[{}]",
                scpId, number, msp1_dnum, com_status);


        LogMessage message = new LogMessage(
                0, System.currentTimeMillis(), scpId,
                Constants.tranSrcSioCom, number, Constants.customTranType, 0, this.toString());
        queueProducer.sendLogMessage(message);


        // 更新sio状态
        int status = Constants.sioStateMap.get(com_status);
        StatusMessage sMessage = new StatusMessage(
                0, System.currentTimeMillis(), scpId,
                Constants.tranSrcScpCom, scpId, Constants.customTranType, 0, status, Constants.mqSourceScp,this.toString());
        queueProducer.sendStatusMessage(sMessage);
    }
}
