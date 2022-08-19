package com.wim.aero.acs.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: AcrRequest
 * @author: Ellie
 * @date: 2022/03/30 10:01
 * @description:
 **/
@Data
@ApiModel(value = "读卡器设防撤防信息")
public class AcrMaskRequestInfo extends TaskRequest {
    @ApiModelProperty(value = "读卡器逻辑编号", required = true)
    private int acrId;

    @ApiModelProperty(value = "控制器id", required = true)
    private int scpId;

    @ApiModelProperty(value = "true 设防 | false 撤防", required = true)
    private boolean flag;
}
