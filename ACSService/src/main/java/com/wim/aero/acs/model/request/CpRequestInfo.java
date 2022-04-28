package com.wim.aero.acs.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: CpRequestInfo
 * @author: Ellie
 * @date: 2022/03/30 10:33
 * @description:
 **/
@Data
@ApiModel(value = "输出点控制命令")
public class CpRequestInfo extends TaskRequest {
    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "控制点逻辑id")
    private int cpId;

    @ApiModelProperty(value = "控制命令类型 1-关闭 2-打开 3-开关")
    private int command;

}
