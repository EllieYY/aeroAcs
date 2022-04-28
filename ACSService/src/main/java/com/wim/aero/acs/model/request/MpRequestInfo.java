package com.wim.aero.acs.model.request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: MpRequestInfo
 * @author: Ellie
 * @date: 2022/03/30 10:22
 * @description:
 **/
@Data
@ApiModel(value = "报警点信息")
public class MpRequestInfo  extends TaskRequest {
    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "报警点/防区逻辑id")
    private int id;

    @ApiModelProperty(value = "设防状态", example = "true - 设防， false - 撤防")
    private boolean setAlarm;
}
