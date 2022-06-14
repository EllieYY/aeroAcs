package com.wim.aero.acs.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @title: DeviceStatusRequest
 * @author: Ellie
 * @date: 2022/06/14 11:21
 * @description:
 **/
@Data
@ApiModel(value = "设备状态请求")
public class DeviceStatusRequest extends TaskRequest {
    @ApiModelProperty(value = "scp设备信息列表")
    List<ScpDeviceInfo> deviceList;
}
