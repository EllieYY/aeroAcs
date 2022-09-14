package com.wim.aero.acs.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: TransactionRequestInfo
 * @author: Ellie
 * @date: 2022/05/07 10:05
 * @description:
 **/
@Data
@ApiModel(value = "事件提取接口参数")
public class TransactionRequestInfo extends  TaskRequest {
    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "事件提取开始序号")
    private long eventStartNo;

    @ApiModelProperty(value = "事件提取结束序号")
    private long eventEndNo;

    @ApiModelProperty(value = "事件提取当前序号")
    private long eventCurNo;
}
