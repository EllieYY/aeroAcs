package com.wim.aero.acs.model.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @title: ScpOfflineResponse
 * @author: Ellie
 * @date: 2022/08/03 14:23
 * @description:
 **/
@Data
public class ScpOfflineResponse {
    @JsonProperty(value = "scpId")
    private int scpId;

    @ApiModelProperty(value = "时间 yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @ApiModelProperty(value = "执行结果代码")
    @JsonProperty(value = "result")
    private int code;

    @ApiModelProperty(value = "执行结果描述")
    @JsonProperty(value = "summary")
    private String reason;
}
