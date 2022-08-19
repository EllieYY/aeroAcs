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
@ApiModel(value = "读卡器远程开门命令信息")
public class AcrUnlockRequestInfo extends TaskRequest {
    @ApiModelProperty(value = "读卡器逻辑编号", required = true)
    private int acrId;

    @ApiModelProperty(value = "控制器id", required = true)
    private int scpId;

    @ApiModelProperty(value = "开门时间（未验证，预留）", example = "", required = false)
    private int strikeTime;
}
