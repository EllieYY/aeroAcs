package com.wim.aero.acs.model.request;

import com.wim.aero.acs.model.command.ScpCmdResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @title: ScmCmdResponseRequest
 * @author: Ellie
 * @date: 2022/06/07 14:22
 * @description:
 **/
@Data
@ApiModel(value = "通信层命令发送响应列表")
public class ScpCmdResponseRequest {
    @ApiModelProperty(value = "通信层命令执行结果列表")
    private List<ScpCmdResponse> responseList;
}
