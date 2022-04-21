package com.wim.aero.acs.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: ScpRequest
 * @author: Ellie
 * @date: 2022/03/28 14:55
 * @description:
 **/
@Data
@ApiModel(value = "控制器参数")
public class ScpRequestInfo {
    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "任务id")
    private int taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务来源")
    private int taskSource;
}
