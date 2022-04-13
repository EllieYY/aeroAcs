package com.wim.aero.acs.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: ScpStateNotify
 * @author: Ellie
 * @date: 2022/04/13 08:17
 * @description:
 **/
@Data
@ApiModel(value = "控制器状态参数")
public class ScpStateNotify {
    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "控制器状态， 0 - 离线  1 - 在线")
    private int state;
}
