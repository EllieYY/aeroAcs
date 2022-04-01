package com.wim.aero.acs.model.scpmessage;

import java.util.List;

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
public class TypeSioComm {
    //  0 - not configured
    //  1 - not tried: active, have not tried to poll it
    //  2 - off-line
    //  3 - on-line
    private int	comm_sts;			// comm status

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
    private List<Byte> mac_addr;			// mac_addr[6];MAC Address, if applicable, LSB first.

}
