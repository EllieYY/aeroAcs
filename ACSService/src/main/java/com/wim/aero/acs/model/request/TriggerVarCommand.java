package com.wim.aero.acs.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: ProcedureCommandRequest
 * @author: Ellie
 * @date: 2022/05/24 11:06
 * @description:
 **/
@Data
@ApiModel(value = "触发器变量")
public class TriggerVarCommand extends TaskRequest {
    @ApiModelProperty(value = "控制器id", required = true)
    private int scpId;

    @ApiModelProperty(value = "触发器变量id", required = true)
    private int varId;
}
