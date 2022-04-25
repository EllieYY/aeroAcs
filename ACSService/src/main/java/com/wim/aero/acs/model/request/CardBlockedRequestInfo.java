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
@ApiModel(value = "卡冻结/解冻请求参数")
public class CardBlockedRequestInfo {
    private long taskId;
    private String taskName;
    private int taskSource;

    @ApiModelProperty(value = "时间组编号")
    private int tz;

    @ApiModelProperty(value = "true-冻结  false-解冻")
    private boolean blocked;

    private List<String> cardList;
}
