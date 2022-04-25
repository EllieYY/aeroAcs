package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.StatusMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @title: TypeSioComm
 * @author: Ellie
 * @date: 2022/04/01 16:20
 * @description:
 *
 * Transaction codes for tranTypeSioComm:
 * //  	1	- comm disabled (result of host command)
 * //  	2	- off-line: timeout (no/bad response from unit)
 * //  	3	- off-line: invalid identification from SIO
 * //  	4	- off-line: Encryption could not be established
 * //  	5	- on-line: normal connection
 * //	6   - hexLoad report: ser_num is address loaded (-1 == last record)
 **/
@Data
@Slf4j
public class TypeSioComm extends TransactionBody {

    private int	comm_sts;			// comm status
                                    //  0 - not configured
                                    //  1 - not tried: active, have not tried to poll it
                                    //  2 - off-line
                                    //  3 - on-line
    private byte model;					// sio model number (VALID ONLY IF "ON-LINE")
    private byte revision;				// sio firmware revision number (VALID ONLY IF "ON-LINE")
    private long ser_num;				// sio serial number (VALID ONLY IF "ON-LINE")
    // extended SIO info: the following block is valid ONLY if nExtendedInfoValid is non-zero:
    private int nExtendedInfoValid;	// use the data below only if this field is non-zero

    //    SIO Hardware ID
    //    HID Aero X1100 217
    //    HID Aero X100 218
    //    HID Aero X200 219
    //    HID Aero X300 220
    //    VertX V100 0
    //    VertX V200 0
    //    VertX V300 0
    private int nHardwareId;

    private int nHardwareRev;
    private int nProductId;			// same as model
    private int nProductVer;			// -- application specific version of this ProductId
    private int nFirmwareBoot;		// BOOT code version info:   (maj(4) << 12)+(min(8) << 4) + (bld(4))
    private int nFirmwareLdr;			// Loader code version info: (maj(4) << 12)+(min(8) << 4) + (bld(4))
    private int nFirmwareApp;			// App code version info:    (maj(4) << 12)+(min(8) << 4) + (bld(4))
    private int nOemCode;				// Not used
    private byte  nEncConfig;			// Master/Secret key currently in use on this SIO: 0=None, 1=AES Default Key, 2=AES Master/Secret Key, 3= PKI, 6=AES256 session key
    private byte  nEncKeyStatus;		// Status of Master/Secret Key; 0=Not Loaded, 1=Loaded, unverified, 2=Loaded, conflicts w/SIO, 3=Loaded, Verified, 4=AES256 Verified.
    private String mac_addr;			// mac_addr[6];MAC Address, if applicable, LSB first.

    @Override
    public void process(QueueProducer queueProducer, SCPReplyTransaction transaction) {
        log.info("[sio状态] - scpId[{}], sio[{}], {}, model[{}], comStatus[{}]",
                transaction.getScpId(), transaction.getSourceNumber(), getHardwareName(nHardwareId), model, getStateName(comm_sts));

        int scpId = transaction.getScpId();
        long date = transaction.getTime() * 1000;
        long index = transaction.getSerNum();
        int sourceType = transaction.getSourceType();
        int sourceNum = transaction.getSourceNumber();
        int tranType = transaction.getTranType();
        int tranCode = transaction.getTranCode();

        // SIO板：0离线 1正常  2报警
        //  0 - not configured
        //  1 - not tried: active, have not tried to poll it
        //  2 - off-line
        //  3 - on-line
        Map<Integer, Integer> statusMap = Map.of(
                0, 0,
                1, 0,
                2, 0,
                3, 1);
        queueProducer.sendStatusMessage(
                new StatusMessage(index, date, scpId,
                        sourceType, sourceNum, tranType, tranCode,
                        statusMap.get(comm_sts), Constants.mqSourceSio, this.toString()));
    }

    //    SIO Hardware ID
    //    HID Aero X1100 217
    //    HID Aero X100 218
    //    HID Aero X200 219
    //    HID Aero X300 220
    //    VertX V100 0
    //    VertX V200 0
    //    VertX V300 0
    private String getHardwareName(int nHardwareId) {
        String name = "未知设备";
        switch (nHardwareId) {
            case 217:
                name = "X1100";
                break;
            case 218:
                name = "X100";
                break;
            case 219:
                name = "X200";
                break;
            case 220:
                name = "X300";
                break;
            default:
                break;
        }

        return name;
    }

    //  0 - not configured
    //  1 - not tried: active, have not tried to poll it
    //  2 - off-line
    //  3 - on-line
    private String getStateName(int state) {
        String name = "未知状态";
        switch (state) {
            case 0:
                name = "not configured";
                break;
            case 1:
                name = "not tried: active, have not tried to poll it";
                break;
            case 2:
                name = "off-line";
                break;
            case 3:
                name = "on-line";
                break;
            default:
                break;
        }

        return name;
    }


}
