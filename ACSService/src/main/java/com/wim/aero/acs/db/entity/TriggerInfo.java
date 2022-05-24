package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

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
 * @since 2022-05-23
 */
@Getter
@Setter
@TableName("trigger_info")
@ApiModel(value = "TriggerInfo对象", description = "")
public class TriggerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "seq_no", type = IdType.AUTO)
    private Integer seqNo;

    @TableField("controller_id")
    private Integer controllerId;

    @TableField("trgr_id")
    private Integer trgrId;

    @TableField("msg_type")
    private String msgType;

    @TableField("event_source_type")
    private Integer eventSourceType;

    @TableField("event_type")
    private Integer eventType;

    @TableField("event_type_code")
    private Integer eventTypeCode;

    @TableField("event_record_id")
    private Integer eventRecordId;

    @TableField("event_name")
    private String eventName;

    @TableField("proc_id")
    private Integer procId;

    @TableField("command_id")
    private Integer commandId;

    @TableField("schedules_group_id")
    private Integer schedulesGroupId;

    @TableField("device_number")
    private Integer deviceNumber;

    @TableField("trig_var1")
    private Integer trigVar1;

    @TableField("trig_var2")
    private Integer trigVar2;

    @TableField("trig_var3")
    private Integer trigVar3;

    @TableField("trig_var4")
    private Integer trigVar4;

    @TableField("var1")
    private Integer var1;

    @TableField("var2")
    private Integer var2;

    @TableField("var3")
    private Integer var3;

    @TableField("var4")
    private Integer var4;

    @TableField("order_num")
    private Integer orderNum;



}
