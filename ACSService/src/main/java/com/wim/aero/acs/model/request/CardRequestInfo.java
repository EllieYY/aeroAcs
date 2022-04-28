package com.wim.aero.acs.model.request;

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
public class CardRequestInfo  extends TaskRequest{
    @ApiModelProperty(value = "卡号冻结时使用的时间组编号")
    private int tz;

    @ApiModelProperty(value = "卡号列表")
    private List<String> cardList;
}
