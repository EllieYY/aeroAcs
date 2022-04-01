package com.wim.aero.acs.model.scpmessage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: ScpReplayNak
 * @author: Ellie
 * @date: 2022/04/01 11:12
 * @description: 错误信息
 **/
@Data
@ApiModel(value = "SCP上报NAK信息")
public class ScpReplayNAK {
    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "NAK原因代码")
    private int reason;

    @ApiModelProperty(value = "NAK数据")
    private long data;

    @ApiModelProperty(value = "产生NAK的报文")
    private String command;

    @ApiModelProperty(value = "NAK详情代码")
    private long descriptionCode;
}
