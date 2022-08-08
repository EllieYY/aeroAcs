package com.wim.aero.acs.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @title: CardDeleteInfo
 * @author: Ellie
 * @date: 2022/07/28 09:44
 * @description:
 **/
@Data
@ApiModel(value = "卡删除信息")
public class CardDeleteInfo{
    @ApiModelProperty(value = "拥有该卡的控制器id列表")
    private List<Integer> scpIdList;

    @ApiModelProperty(value = "卡号")
    private String cardNo;
}
