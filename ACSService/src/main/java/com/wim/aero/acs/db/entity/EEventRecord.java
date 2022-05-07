package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2022-05-06
 */
@Getter
@Setter
@TableName("e_event_record")
@ApiModel(value = "EEventRecord对象", description = "")
public class EEventRecord implements Serializable {

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


}
