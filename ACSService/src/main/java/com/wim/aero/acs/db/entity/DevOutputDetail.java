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
@TableName("dev_output_detail")
@ApiModel(value = "DevOutputDetail对象", description = "")
public class DevOutputDetail implements Serializable {

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

    @TableField("output_type_id")
    private Integer outputTypeId;

    @TableField("output")
    private Integer output;

    @TableField("device_name")
    private String deviceName;

    @TableField("mode_flag")
    private String modeFlag;

    @TableField("pulse_flag")
    private String pulseFlag;

    @TableField("pulse_cycle")
    private Integer pulseCycle;

    @TableField("schedules_group_id")
    private Integer schedulesGroupId;

    @TableField("cp_number")
    private Integer cpNumber;

    @TableField("status")
    private String status;

    @TableField("remark")
    private String remark;


}
