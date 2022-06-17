package com.wim.aero.acs.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @title: AlvlRequestInfo
 * @author: Ellie
 * @date: 2022/05/17 14:30
 * @description:
 **/
@Data
@ApiModel(value = "指定访问级别请求参数")
public class AlvlListRequestInfo extends TaskRequest {
    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "是否梯控")
    @JsonProperty(value = "isEle")
    private boolean isEle;

    @ApiModelProperty(value = "访问级别列表")
    private List<Integer> alvList;
}
