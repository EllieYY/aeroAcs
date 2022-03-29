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
public class ScpRequest {
    @ApiModelProperty(value = "控制器id")
    private int scpId;
}
