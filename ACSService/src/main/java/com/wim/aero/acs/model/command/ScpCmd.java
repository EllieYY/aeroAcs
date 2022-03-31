package com.wim.aero.acs.model.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: ScpCmd
 * @author: Ellie
 * @date: 2022/03/28 11:17
 * @description: 控制器报文
 **/
@Data
@ApiModel(value = "控制器报文")
public class ScpCmd {
    @ApiModelProperty(value = "控制器id")
    @JsonProperty("scpId")
    private String scpId;

    @ApiModelProperty(value = "报文")
    @JsonProperty("scpCommand")
    private String command;

    @ApiModelProperty(value = "报文编号")
    @JsonProperty("streamId")
    private String streamId;

    public ScpCmd(int scpId, String command, String streamId) {
        this.scpId = String.valueOf(scpId);
        this.command = command;
        this.streamId = String.valueOf(streamId);
    }
}
