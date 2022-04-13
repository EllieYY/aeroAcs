package com.wim.aero.acs.model.scp.reply;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wim.aero.acs.util.StringUtil;
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
public class SCPReplyNAK extends ReplyBody {
    @ApiModelProperty(value = "控制器id")
    @JsonProperty("ScpId")
    private int scpId;

    @ApiModelProperty(value = "NAK原因代码")
    @JsonProperty("Reason")
    @Positive
    private int reason;

    @ApiModelProperty(value = "NAK数据")
    @JsonProperty("Data")
    private long data;

    @ApiModelProperty(value = "产生NAK的报文")
    @JsonProperty("Command")
    private String command;

    @ApiModelProperty(value = "NAK详情代码")
    @JsonProperty("DescriptionCode")
    private long descriptionCode;

}