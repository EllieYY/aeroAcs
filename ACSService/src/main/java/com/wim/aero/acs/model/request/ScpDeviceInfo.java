package com.wim.aero.acs.model.request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @title: ScpDeviceInfo
 * @author: Ellie
 * @date: 2022/06/14 11:16
 * @description:
 **/
@ApiModel(value = "控制器设备列表")
@Data
public class ScpDeviceInfo {
    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "sio板编号")
    private List<Integer> sioList;

    @ApiModelProperty(value = "控制点编号 - 逻辑编号")
    private List<Integer> cpList;

    @ApiModelProperty(value = "报警点编号 - 逻辑编号")
    private List<Integer> mpList;

    @ApiModelProperty(value = "读卡器编号 - 逻辑编号")
    private List<Integer> acrList;
}
