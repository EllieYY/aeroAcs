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
@ApiModel(value = "读卡器模式设置信息")
public class AcrRequestInfo extends TaskRequest {
    @ApiModelProperty(value = "读卡器逻辑编号")
    private int id;

    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "模式值 1 - 禁用  2 - 常开 3 - 常关 4 - 工程号开门 5 - 只刷卡 6 - 只PIN码 7 - 刷卡加PIN码 8 - 刷卡或PIN码", example = "")
    private int command;
}
