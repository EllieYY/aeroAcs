package com.wim.aero.acs.model.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @title: ScpConnectInfo
 * @author: Ellie
 * @date: 2022/08/18 15:30
 * @description:
 **/
@Data
@ApiModel(value = "控制器连接信息")
public class ScpConnectInfo {
    @ApiModelProperty(value = "控制器id")
    @JsonProperty("scpId")
    private String scpId;

    @ApiModelProperty(value = "ip地址")
    @JsonProperty("ip")
    private String ip;

    @ApiModelProperty(value = "报文")
    @JsonProperty("scpCommand")
    private String command;

    public ScpConnectInfo(int scpId, String ipStr, String command) {
        this.scpId = String.valueOf(scpId);
        this.command = command;
        this.ip = ipStr;
    }
}
