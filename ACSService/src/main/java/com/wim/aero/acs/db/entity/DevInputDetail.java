package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @since 2022-03-22
 */
@Getter
@Setter
@TableName("dev_input_detail")
@ApiModel(value = "DevInputDetail对象", description = "")
public class DevInputDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "device_id", type = IdType.AUTO)
    private Integer deviceId;

    @TableField("device_type_id")
    private Integer deviceTypeId;

    @TableField("device_scope_id")
    private Integer deviceScopeId;

    @TableField("p_device_type_id")
    private Integer pDeviceTypeId;

    @TableField("p_device_id")
    private Integer pDeviceId;

    @TableField("controller_id")
    private Integer controllerId;

    @TableField("input_type_id")
    private Integer inputTypeId;

    @TableField("input")
    private Integer input;

    @TableField("device_name")
    private String deviceName;

    @TableField("status")
    private String status;

    @TableField("shield_flag")
    private String shieldFlag;

    @TableField("alarm_delay_flag")
    private String alarmDelayFlag;

    @TableField("in_delay")
    private Integer inDelay;

    @TableField("out_delay")
    private Integer outDelay;

    @TableField("in_mode")
    private String inMode;

    @TableField("mp_number")
    private Integer mpNumber;

    @TableField("area_id")
    private Integer areaId;

    @TableField("remark")
    private String remark;

    @TableField("sio_number")
    private Integer sioNumber;


}
