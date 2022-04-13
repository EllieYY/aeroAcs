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
public class AcrRequestInfo {
    @ApiModelProperty(value = "读卡器逻辑编号")
    private int acrId;

    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "锁对应输出点逻辑编号")
    private int strikeNo;

    @ApiModelProperty(value = "控制命令 1 - 关闭  2 - 打开or常开 3 - 常关", example = "")
    private int command;
}
