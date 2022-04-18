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
@TableName("e_access_record")
@ApiModel(value = "EAccessRecord对象", description = "")
public class EAccessRecord implements Serializable {

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

    @TableField("reader_id")
    private Integer readerId;

    @TableField("card_no")
    private String cardNo;

    @TableField("employee_no")
    private String employeeNo;

    @TableField("employee_name")
    private String employeeName;

    @TableField("dept_id")
    private Integer deptId;

    @TableField("dept_name")
    private String deptName;

    @TableField(value = "ins_time", fill = FieldFill.INSERT)
    private Date insTime;

    @TableField("full_memo")
    private String fullMemo;

    public EAccessRecord(Long eventIndex, Date eventsTime,
                         Integer controllerId,
                         Integer eventSourceType, Integer sourceCode,
                         Integer eventType, Integer eventTypeCode,
                         String cardNo,
                         String fullMemo) {
        this.eventIndex = eventIndex;
        this.eventsTime = eventsTime;
        this.controllerId = controllerId;
        this.eventSourceType = eventSourceType;
        this.sourceCode = sourceCode;
        this.eventType = eventType;
        this.eventTypeCode = eventTypeCode;
        this.cardNo = cardNo;
        this.fullMemo = fullMemo;
    }
}
