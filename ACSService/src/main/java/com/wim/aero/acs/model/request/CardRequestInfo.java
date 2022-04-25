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
public class CardRequestInfo {

    private long taskId;
    private String taskName;
    private int taskSource;

    private int tz;
    private List<String> cardList;
}
