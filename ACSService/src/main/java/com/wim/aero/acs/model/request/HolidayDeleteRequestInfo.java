package com.wim.aero.acs.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @title: AcrRequest
 * @author: Ellie
 * @date: 2022/03/30 10:01
 * @description:
 **/
@Data
@ApiModel(value = "假日删除参数")
public class HolidayDeleteRequestInfo extends TaskRequest {
    @ApiModelProperty(value = "假日编号列表")
    private List<Integer> holidayIdList;

    @ApiModelProperty(value = "控制器id")
    private int scpId;
}
