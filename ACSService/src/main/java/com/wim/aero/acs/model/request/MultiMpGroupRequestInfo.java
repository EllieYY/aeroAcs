package com.wim.aero.acs.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @title: MpRequestInfo
 * @author: Ellie
 * @date: 2022/03/30 10:22
 * @description:
 **/
@Data
@ApiModel(value = "防区信息")
public class MultiMpGroupRequestInfo extends TaskRequest {
    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "防区逻辑id列表")
    private List<Integer> mpgIdList;

    @ApiModelProperty(value = "设防状态", example = "true - 设防， false - 撤防")
    private boolean setAlarm;
}
