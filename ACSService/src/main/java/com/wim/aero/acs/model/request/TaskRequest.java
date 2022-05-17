package com.wim.aero.acs.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: TaskRequest
 * @author: Ellie
 * @date: 2022/04/28 08:26
 * @description:
 **/
@ApiModel(value = "任务请求")
@Data
public class TaskRequest {
    @ApiModelProperty(value = "任务id")
    private long taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务来源")
    private int taskSource;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private boolean reloadFlag = false;    // 重发标记
}
