package com.wim.aero.acs.model.scp.reply;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.service.QueueProducer;
import com.wim.aero.acs.service.ScpCenter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: SCPReplyTranStatus
 * @author: Ellie
 * @date: 2022/04/01 11:14
 * @description: 控制器事务上报状态信息
 **/
@Data
@ApiModel(value = "transaction状态信息")
public class SCPReplyTranStatus extends ReplyBody {
    @ApiModelProperty(value = "scpId")
    private int scpId;

    @ApiModelProperty(value = "capacity")
    private long capacity;		// the transaction buffer size (allocated)

    @ApiModelProperty(value = "oldest")
    private long oldest;			// serial number of the oldest TR in the file

    @ApiModelProperty(value = "lastRprtd")
    private long lastRprtd;		// serial number of the last reported TR

    @ApiModelProperty(value = "lastLoggd")
    private long lastLoggd;		// serial number of the last logged TR

    @ApiModelProperty(value = "disabled")
    private int disabled;		// non-zero if disabled with (Command_303)

    @Override
    public void process(QueueProducer queueProducer, int scpId) {
        ScpCenter.updateTR(scpId, oldest, lastRprtd);
        LogMessage message = new LogMessage(
                0, System.currentTimeMillis(), scpId,
                Constants.tranSrcScpCom, scpId, Constants.customTranType, 0, this.toString());
        queueProducer.sendLogMessage(message);
    }
}
