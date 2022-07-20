package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ellie
 * @since 2022-07-18
 */
@Getter
@Setter
@TableName("e_event_code_detail_dev")
@ApiModel(value = "EEventCodeDetailDev对象", description = "")
public class EEventCodeDetailDev implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "seq_no", type = IdType.AUTO)
    private Integer seqNo;

    @TableField("event_record_id")
    private Integer eventRecordId;

    @TableField("event_source_type")
    private Integer eventSourceType;

    @TableField("source_code")
    private Integer sourceCode;

    @TableField("event_type")
    private Integer eventType;

    @TableField("event_type_code")
    private Integer eventTypeCode;

    @TableField("change_state")
    private Integer changeState;

    @TableField("event_memo")
    private String eventMemo;

    @TableField("translate_memo")
    private String translateMemo;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private LocalDateTime updateTime;


}
