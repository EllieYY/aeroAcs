package com.wim.aero.acs.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wim.aero.acs.config.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: CardCmd
 * @author: Ellie
 * @date: 2022/08/02 09:32
 * @description:
 **/
@Data
@ApiModel(value = "授权命令信息")
public class CardCmd {
    @ApiModelProperty(value = "控制器id", required = true)
    @JsonProperty("scpId")
    private int scpId;

    @ApiModelProperty(value = "卡号", required = true)
    private String cardNo = "";

    @ApiModelProperty(value = "访问级别列表", required = true)
    private String alvlListStr = "0";

    @ApiModelProperty(value = "报文", required = true)
    private String command;

    @JsonIgnore
    private int type = Constants.SCP_CMD_CARD_ADD;
}
