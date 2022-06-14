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
@ApiModel(value = "读卡器远程控制命令信息")
public class AcrRequestInfo extends TaskRequest {
    @ApiModelProperty(value = "读卡器逻辑编号(常开常闭命令)或控制点逻辑编号（打开、关闭命令）")
    private int id;

    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "控制命令 1 - 关闭  2 - 打开or常开 3 - 常关 4 - 工程号开门 5 - 只刷卡 6 - 只PIN码 7 - 刷卡加PIN码 8 - 刷卡或PIN码", example = "")
    private int command;
}
