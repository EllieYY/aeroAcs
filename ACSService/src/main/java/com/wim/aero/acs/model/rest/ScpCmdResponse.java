package com.wim.aero.acs.model.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @title: ScpCmdResponse
 * @author: Ellie
 * @date: 2022/03/28 11:17
 * @description:
 **/
@Data
@ApiModel(value = "报文执行结果")
public class ScpCmdResponse {
    @ApiModelProperty(value = "报文编号")
    @JsonProperty(value = "streamId")
    private String streamId;

    @ApiModelProperty(value = "时间 yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @ApiModelProperty(value = "执行结果代码")
    @JsonProperty(value = "cmdSendResult")
    private String code;

    @ApiModelProperty(value = "执行结果描述")
    @JsonProperty(value = "summary")
    private String reason;

//    public ScpCmdResponse(String streamId, Date date, String code, String reason) {
//        this.streamId = Long.parseLong(streamId);
//        this.date = date;
//        this.code = Integer.parseInt(code);
//        this.reason = reason;
//    }
}
