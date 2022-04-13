package com.wim.aero.acs.model.scp.reply;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wim.aero.acs.model.scp.transaction.TransactionType;
import com.wim.aero.acs.util.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;

/**
 * @title: ScpReply
 * @author: Ellie
 * @date: 2022/04/13 11:16
 * @description:
 **/
@Data
@ApiModel(value = "SCPReply数据")
@Validated
public class SCPReply {
    @ApiModelProperty(value = "控制器id")
    @JsonProperty(value = "SCPId")
    private int scpId;

    @ApiModelProperty(value = "数据类型")
    @JsonProperty(value = "ReplyType")
    private int type;

    @ApiModelProperty(value = "数据内容")
    @JsonProperty(value = "Content")
    private String content;

}
