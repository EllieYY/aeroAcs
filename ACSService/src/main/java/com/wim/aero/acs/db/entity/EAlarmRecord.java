package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
 * @since 2022-04-07
 */
@Getter
@Setter
@TableName("e_alarm_record")
@ApiModel(value = "EAlarmRecord对象", description = "")
public class EAlarmRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "seq_no", type = IdType.AUTO)
    private Integer seqNo;

    @TableField("event_index")
    private Long eventIndex;

    @TableField("events_time")
    private Date eventsTime;

    @TableField("controller_id")
    private Integer controllerId;

    @TableField("event_source_type")
    private Integer eventSourceType;

    @TableField("source_code")
    private Integer sourceCode;

    @TableField("event_type")
    private Integer eventType;

    @TableField("event_type_code")
    private Integer eventTypeCode;

    @TableField(value = "ins_time", fill = FieldFill.INSERT)
    private Date insTime;

    @TableField("deal_flag")
    private String dealFlag;

    @TableField("full_memo")
    private String fullMemo;

    public EAlarmRecord(Long eventIndex, Date eventsTime,
                        Integer controllerId,
                        Integer eventSourceType, Integer sourceCode,
                        Integer eventType, Integer eventTypeCode,
                        String fullMemo) {
        this.eventIndex = eventIndex;
        this.eventsTime = eventsTime;
        this.controllerId = controllerId;
        this.eventSourceType = eventSourceType;
        this.sourceCode = sourceCode;
        this.eventType = eventType;
        this.eventTypeCode = eventTypeCode;
        this.fullMemo = fullMemo;
    }
}
