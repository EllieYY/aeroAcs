package com.wim.aero.acs.model.scpmessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: SCPReplyTransaction
 * @author: Ellie
 * @date: 2022/04/01 11:13
 * @description: 事务信息
 **/
@Data
@ApiModel(value = "SCPReplyTransaction信息")
public class SCPReplyTransaction {
    @ApiModelProperty(value = "scpId")
    private int scpId;

    @ApiModelProperty(value = "transaction序号")
    private long serNum;			// serial number of this transaction

    @ApiModelProperty(value = "transaction时间，到1970秒数")
    private long time;				// time of the transaction, seconds, 1970-based

    @ApiModelProperty(value = "sourceType")
    private int sourceType;		// see the "tranSrc..." definitions

    @ApiModelProperty(value = "sourceNumber")
    private int sourceNumber;		// ...defines the element of tranSrc...

    @ApiModelProperty(value = "tranType")
    private int tranType;			// see the "tranType..." definitions

    @ApiModelProperty(value = "tranCode")
    private int tranCode;			// ...defines the reason

    @ApiModelProperty(value = "tranType对应结构体进行Json结构体序列化字符串")
    private String argJsonStr;
}
