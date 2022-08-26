package com.wim.aero.acs.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @title: CardListInfo
 * @author: Ellie
 * @date: 2022/04/18 19:02
 * @description:
 **/
@Data
@ApiModel(value = "卡号列表")
public class CardRequestInfo  extends TaskRequest {
    @ApiModelProperty(value = "卡号列表")
    private List<String> cardList;

    @ApiModelProperty(value = "是否是梯控设备加卡，删卡接口可不填此参数")
    @JsonProperty(defaultValue = "false")
    private boolean isEleScp;

    @ApiModelProperty(value = "控制器id")
    @JsonProperty(defaultValue = "0")
    private int scpId;
}
