package com.wim.aero.acs.model.scp.reply;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.service.QueueProducer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;

/**
 * @title: ScpReplayNak
 * @author: Ellie
 * @date: 2022/04/01 11:12
 * @description: 错误信息
 **/
@Data
@ApiModel(value = "SCP上报NAK信息")
@Validated
public class SCPReplyNAKStr extends ReplyBody {
    @ApiModelProperty(value = "NAK原因代码")
    @JsonProperty("reason")
    @Positive
    private String reason;

    @ApiModelProperty(value = "NAK数据")
    @JsonProperty("data")
    private String data;

    @ApiModelProperty(value = "产生NAK的报文")
    @JsonProperty("command")
    private String command;

    @ApiModelProperty(value = "NAK详情代码")
    @JsonProperty("description_code")
    private String descriptionCode;

    @Override
    public void process(QueueProducer queueProducer, int scpId) {
        LogMessage message = new LogMessage(
                0, System.currentTimeMillis(), scpId,
                Constants.mqSourceScp, scpId, Constants.customTranType, 0, this.toString());
        queueProducer.sendLogMessage(message);
    }
}
