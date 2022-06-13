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
@ApiModel(value = "卡对应的单个门冻结-解冻or挂失-解挂请求参数")
public class CardBlockedRequestInfo extends TaskRequest {
    @ApiModelProperty(value = "时间组编号")
    @JsonProperty(defaultValue = "0")
    private int tz;

//    @ApiModelProperty(value = "true-冻结or挂失  false-解冻or解挂")
//    private int readerId blocked;

    @ApiModelProperty(value = "true-冻结or挂失  false-解冻or解挂")
    private boolean blocked;

    @ApiModelProperty(value = "卡号列表")
    private List<String> cardList;
}
