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
    @ApiModelProperty(value = "要冻结的读卡器逻辑id")
    private List<Integer> readerIdList;

    @ApiModelProperty(value = "卡号")
    private String cardNo;
}
