package com.wim.aero.acs.config;

/**
 * @description 常量定义
 */
public interface Constants {
    String ENCODER_TO_STR = "toStr";
    String IP_PORT_SPLITTER = ":";

    String OPERATION_PREFIX = "operation-";

    int REST_CODE_SUCCESS = 0;

    // 命令执行状态
    // - 0 = FAILED (could not send, SCP off-line)
    // - 1 = OK (delivered and accepted),
    // - 2 = NAK'd (command rejected by the SCP)
    int CMND_FAILED = 0;
    int CMND_OK = 1;
    int CMND_NAK = 2;


    // transaction related definitions
    // - transaction source type definitions
    int tranSrcScpDiag = 0x00;	// SCP diagnostics
    int tranSrcScpCom = 0x01;	// SCP to HOST comm driver
    int tranSrcScpLcl = 0x02;	// SCP local monitor points
    int tranSrcSioDiag = 0x03;	// SIO diagnostics
    int tranSrcSioCom = 0x04;	// SIO comm driver
    int tranSrcSioTmpr = 0x05;	// SIO cabinet tamper
    int tranSrcSioPwr = 0x06;	// SIO power monitor
    int tranSrcMP = 0x07;	// alarm Monitor Point
    int tranSrcCP = 0x08;	// output Control Point
    int tranSrcACR = 0x09;	// Access Control Reader (ACR)
    int tranSrcAcrTmpr = 0x0A;	// ACR: reader tamper monitor
    int tranSrcAcrDoor = 0x0B;	// ACR: door position sensor
    int tranSrcAcrRex0 = 0x0D;	// ACR: 1'st "Request to exit" input
    int tranSrcAcrRex1 = 0x0E;	// ACR: 2'nd "Request to exit" input
    int tranSrcTimeZone = 0x0F;	// Timezone
    int tranSrcProcedure = 0x10;	// procedure (action list)
    int tranSrcTrigger = 0x11;	// trigger
    int tranSrcTrigVar = 0x12;	// trigger variable
    int tranSrcMPG = 0x13;	// Monitor Point Group
    int tranSrcArea = 0x14;	// Access Control Area
    int tranSrcAcrTmprAlt = 0x15;	// ACR: the alternate reader's tamper monitor
    int tranSrcSioEmg = 0x17;	// SIO Emergency Switch
    int tranSrcLoginService	= 0x18;	// Login Service

    // - transaction type definitions - specifies the transaction argument also
    int tranTypeSys = 0x01;	// system
    int tranTypeSioComm = 0x02;	// SIO communication status report
    int tranTypeCardBin = 0x03;	// binary card data
    int tranTypeCardBcd = 0x04;	// card data
    int tranTypeCardFull = 0x05;	// formatted card: f/c, c/n, i/c
    int tranTypeCardID = 0x06;	// formatted card: card number only
    int tranTypeCoS = 0x07;	// change-of-state
    int tranTypeREX = 0x08;	// exit request
    int tranTypeCoSDoor = 0x09;	// Door Sts Monitor Change-Of-State
    int tranTypeProcedure = 0x0A;	// procedure (command list) log
    int tranTypeUserCmnd = 0x0B;	// User Command Request report
    int tranTypeActivate = 0x0C;	// change of state: tv, tz, trig
    int tranTypeAcr = 0x0D;	// ACR mode change
    int tranTypeMpg = 0x0E;	// Monitor Point Group status change
    int tranTypeArea = 0x0F;	// Access Control Area
    int tranTypeOAL = 0x19;	// Offline Access List
    int tranTypeUseLimit = 0x13;	// Use Limit report
    int tranTypeWebActivity = 0x14;	// Web activity
    int tranTypeDblCardFull = 0x15;	// formatted card: f/c, c/n-64-bit, i/c
    int tranTypeDblCardID = 0x16;	// formatted card: 52-bit card number only
    int tranTypeOperatingMode = 0x18;	// Operating Mode change
    int tranTypeCoSElevator = 0x1A;	// Elevator Floor Status CoS
    int tranTypeFileDownloadStatus = 0x1B;	// File Download Status
    int tranTypeBatchReport = 0x1C;	// Batch Report
    int tranTypeCoSElevatorAccess = 0x1D;	// Elevator Floor Access Transaction
    int tranTypeI64CardFull	= 0x25;	// formatted card: f/c, c/n-64-bit, i/c
    int tranTypeI64CardID = 0x26;	// formatted card: 64-bit card number only
    int tranTypeI64CardFullIc32 = 0x35;	// formatted card: f/c, c/n-64-bit, 32-bit i/c
    int	tranTypeAcrExtFeatureStls = 0x40;	// Extended Lockset Stateless event
    int	tranTypeAcrExtFeatureCoS = 0x41;	// Extended Locket Statefull transaction
    int tranTypeAsci = 0x7E;	// ASCII diagnostic message
    int tranTypeSioDiag	= 0x7F;	// SIO comm diagnostics



    // 通信服务定义的错误码
//    public static readonly int ERR_SUCCED = 0;
//
//    public static readonly int ERR_FAIL_2_SCP = 1;
//
//    public static readonly int ERR_SCP_NOT_CREATE = 2;
//
//    public static readonly int ERR_CHANNEL_NOT_CREATE = 3;
//
//    public static readonly int ERR_DRIVER_UNINIT = 4;
//
//    public static readonly int ERR_SCP_RECREATE = 5;
//
//    public static readonly int ERR_SCP_ATTACH_CHANNEL = 6;
//
//    public static readonly int ERR_CMD_INVALID = 7;
//
//    static status()
//    {
//        err_msg.Add(ERR_SUCCED,                 "向SCP发送命令成功");
//        err_msg.Add(ERR_FAIL_2_SCP,             "向SCP发送命令失败");
//        err_msg.Add(ERR_SCP_NOT_CREATE,         "SCP未创建");
//        err_msg.Add(ERR_CHANNEL_NOT_CREATE,     "Channel未创建");
//        err_msg.Add(ERR_DRIVER_UNINIT,          "驱动未初始化");
//        err_msg.Add(ERR_SCP_RECREATE,           "重复创建SCP");
//        err_msg.Add(ERR_SCP_ATTACH_CHANNEL,     "SCP绑定Channel失败");
//        err_msg.Add(ERR_CMD_INVALID,            "SCP命令非法");
//    }

}
