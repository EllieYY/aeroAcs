package com.wim.aero.acs.model.scpmessage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wim.aero.acs.util.Date2LongSerializer;
import com.wim.aero.acs.util.JsonUtil;
import com.wim.aero.acs.util.Long2DateDeserializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @title: SCPReplyTransaction
 * @author: Ellie
 * @date: 2022/04/01 11:13
 * @description: 事务信息
 **/
@Data
@ApiModel(value = "SCPReplyTransaction信息")
public class SCPReplyTransaction<T extends TransactionBody> {
    @ApiModelProperty(value = "scpId")
    private int scpId;

    @ApiModelProperty(value = "transaction序号")
    private long serNum;			// serial number of this transaction

    @ApiModelProperty(value = "transaction时间，到1970秒数")
    @JsonDeserialize(using = Long2DateDeserializer.class)
    private Date time;				// time of the transaction, seconds, 1970-based

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

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private T body;

    // Json转换
    public T updateTransactionBody() {
        Class<T> bodyClazz = TransactionType.fromCode(this.sourceType, this.tranType).getTransClazz();
        T body = JsonUtil.fromJson(argJsonStr, bodyClazz);
        this.body = body;

        return body;
    }
}
