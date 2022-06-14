package com.wim.aero.acs.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @title: ScpCardsRequest
 * @author: Ellie
 * @date: 2022/06/14 14:07
 * @description:
 **/
@Data
@ApiModel(value = "控制器卡容量查询")
public class ScpCardsRequest extends TaskRequest {

    @ApiModelProperty(value = "控制器列表")
    private List<Integer> scpList;
}
