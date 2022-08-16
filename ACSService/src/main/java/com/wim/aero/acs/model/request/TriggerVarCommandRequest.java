package com.wim.aero.acs.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @title: ProcedureCommandRequest
 * @author: Ellie
 * @date: 2022/05/24 11:06
 * @description:
 **/
@Data
@ApiModel(value = "触发器变量删除参数")
public class TriggerVarCommandRequest extends TaskRequest {
    @ApiModelProperty(value = "触发器变量列表", required = true)
    private List<TriggerVarCommand> infoList;
}

