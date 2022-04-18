package com.wim.aero.acs.model.scp.reply;

import java.util.function.Predicate;

public enum EnScpReplyType {
    enSCPReplyUnknown(0),			// 00 - not recognized: passed on as is
    enSCPReplyACK(1),				// 01 - ACK
    enSCPReplyCommStatus(2),		// 02 - comm_status
    enSCPReplyNAK(3),				// 03 - NAK
    enSCPReplyIDReport(4),			// 04 - ID report
    enSCPReplyUTAGReport(5),		// 05 - UTAG report
    enSCPReplyTranStatus(6),		// 06 - transaction log status
    enSCPReplyTransaction(7),		// 07 - transaction log event
    enSCPReplySrMsp1Drvr(8),		// 08 - status: MSP1 (SIO comm) driver
    enSCPReplySrSio(9),			// 09 - status: SIO
    enSCPReplySrMp(10),				// 10 - status: Monitor Point
    enSCPReplySrCp(11),				// 11 - status: Control Point
    enSCPReplySrAcr(12),			// 12 - status: Access Control Reader
    enSCPReplySrTz(13),				// 13 - status: Timezone
    enSCPReplySrTv(14),				// 14 - status: Trigger Variable
    enSCPReplyCmndStatus(15),		// 15 - Direct command delivery status
    enSCPReplySrMpg(16),			// 16 - status: Monitor Point Group
    enSCPReplySrArea(17),			// 17 - status: Access Area
    enSCPReplyDualPort(18),			// 18 - Dual Port Status
    enSCPReplyBioAddResult(19),		// 19 - Bio Add/Modify/Delete Result
    enSCPReplyStrStatus(20),		// 20 - SCP Structure Status report
    enSCPReplySrIps(21),			// 21 - IPS Group Status report
    enSCPReplySrIpsPts(22),			// 22 - IPS Group Status with Point List report
    enSCPReplyCmndStatusExt(23),    // 23 - Extended Direct command delivery status
    enSCPReplyLoginInfo(24),		// 24 - Login info
    enSCPReplyPkgInfo(25),			// 25 - Installed package information
    enSCPReplyWebConfigNotes(26),			// 26 - Web Config Notes
    enSCPReplyWebConfigNetwork(27),			// 27 - Web Config Network
    enSCPReplyWebConfigHostCommPrim(28),	// 28 - Web Config Host Comm
    enSCPReplyWebConfigHostCommAlt(29),		// 29 - Web Config Host Comm
    enSCPReplyWebConfigSessionTmr(30),		// 30 - Web Config Session Timer
    enSCPReplyWebConfigWebConn(31),			// 31 - Web Config Web Connection
    enSCPReplyWebConfigAutoSave(32),		// 32 - Web Config Auto Save
    enSCPReplyWebConfigNetDiag(33),			// 33 - Web Config Network Diagnostic
    enSCPReplyWebConfigTimeServer(34),		// 34 - Web Config Time Server
    enSCPReplyWebConfigCentralStation(35),	// 35 - Web Config Central station
    enSCPReplyWebConfigCardDBSize(36),		// 36 - Web Config Card DB Size
    enSCPReplyWebConfigDiagnostics(37),		// 37 - Web Config Diagnostics
    enSCPReplyCertInfo(38),					// 38 - Web Config Cert Info
    enSCPReplyElevRelayInfo(39),				// 39 - Elevator Relay Status Info
    enSCPReplySrFileInfo(40),				//40 - File Information
    enSCPReplyOsdpPassthrough(50),    // 50 - OSDP passthrough
    enSCPReplySioRelayCounts(51),		// 51 - SIO relay counts
    enSCPReplyHidMfgInfo(52);		//52 - HID Serial No and UUID

    EnScpReplyType(int code) {
        this.code = code;
    }

    private int code;

    public static EnScpReplyType fromCode(int code){
        return getTransactionType(requestType -> (requestType.code == code));
    }

    private static EnScpReplyType getTransactionType(Predicate<EnScpReplyType> predicate){
        EnScpReplyType[] values = values();
        for (EnScpReplyType operationType : values) {
            if(predicate.test(operationType)){
                return operationType;
            }
        }

        return enSCPReplyUnknown;
    }
}
