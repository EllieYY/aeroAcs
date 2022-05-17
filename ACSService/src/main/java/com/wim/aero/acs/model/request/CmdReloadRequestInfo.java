package com.wim.aero.acs.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wim.aero.acs.model.command.ScpCmd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @title: CmdReloadRequestInfo
 * @author: Ellie
 * @date: 2022/05/17 14:41
 * @description:
 **/
@Data
@ApiModel(value = "指令重发参数")
public class CmdReloadRequestInfo extends TaskRequest {

    @ApiModelProperty(value = "命令列表")
    private List<ScpCmd> cmdList;
}
