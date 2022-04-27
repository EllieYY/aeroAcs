package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.StatusMessage;
import com.wim.aero.acs.service.QueueProducer;
import com.wim.aero.acs.service.ScpCenter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * @title: SCPReplyCommStatus
 * @author: Ellie
 * @date: 2022/04/13 11:34
 * @description: type=2
 **/
@Data
@Slf4j
public class SCPReplyCommStatus extends ReplyBody {
    private int status;                     // enSCPComm
                                            // 0 == enSCPCommUnknown,
                                            // 1 == enSCPCommFailed,
                                            // 2 == enSCPCommOk;
    private enSCPCommErr error_code;        // enSCPCommErr
    private int  nChannelId;			    // channel number - valid if status==enSCPComNoError
                                            // extended reporting for DualPort applications
    private int	current_primary_comm;		// 0 == detached, 1 == not tried, 2 == off-line, 3 == on-line
    private int	previous_primary_comm;		// 0 == detached, 1 == not tried, 2 == off-line, 3 == on-line
    private int	current_alternate_comm;		// Not used
    private int	previous_alternate_comm;	// Not used

    @Override
    public void process(QueueProducer queueProducer, int scpId) {
        // 设备状态：0 - 离线/无效  1 - 在线/正常  2 - 报警  3 - 故障 4 - 打开  5 - 关闭
        int deviceState = 0;
        if (status == 2) {
            deviceState = 1;
            log.info("[设备上线] - scpId:[{}], channelId[{}], curComm[{}], preComm[{}]",
                    scpId, nChannelId, current_primary_comm, previous_primary_comm);
            ScpCenter.scpOnline(scpId);

        } else {
            log.info("[设备离线] - scpId:[{}], status[{}], errorCode[{}], curComm[{}], preComm[{}]",
                    scpId, status, error_code, current_primary_comm, previous_primary_comm);
            ScpCenter.scpOffline(scpId);
        }

        StatusMessage message = new StatusMessage(
                0, System.currentTimeMillis(), scpId,
                Constants.tranSrcScpCom, scpId, Constants.customTranType, 0, deviceState,
                Constants.mqSourceScp, this.toString());
        queueProducer.sendStatusMessage(message);
    }
}
