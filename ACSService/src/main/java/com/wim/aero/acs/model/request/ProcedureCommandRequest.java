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
@ApiModel(value = "过程执行命令参数")
public class ProcedureCommandRequest extends TaskRequest {
    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "过程id")
    private int procedureId;

    @ApiModelProperty(value = "执行动作前缀: [0 256 512 1024]")
    private int prefix;
}
