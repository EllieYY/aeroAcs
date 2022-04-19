package com.wim.aero.acs.db.entity;

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
 * @since 2022-03-22
 */
@Getter
@Setter
@TableName("dev_x_detail")
@ApiModel(value = "DevXDetail对象", description = "")
public class DevXDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("设备ID")
    @TableId("device_id")
    private Integer deviceId;

    @ApiModelProperty("设备类型ID")
    @TableField("device_type_id")
    private Integer deviceTypeId;

    @ApiModelProperty("设备作用ID")
    @TableField("device_scope_id")
    private Integer deviceScopeId;

    @ApiModelProperty("控制器ID")
    @TableField("controller_id")
    private Integer controllerId;

    @ApiModelProperty("设备名称")
    @TableField("device_name")
    private String deviceName;

    @ApiModelProperty("控制器端口号")
    @TableField("controller_port")
    private Integer controllerPort;

    @ApiModelProperty("地址")
    @TableField("address")
    private Integer address;

    @ApiModelProperty("板子类型")
    @TableField("model")
    private Integer model;

    @TableField("sio_next_in")
    private Integer sioNextIn;

    @TableField("sio_next_out")
    private Integer sioNextOut;

    @TableField("sio_next_rdr")
    private Integer sioNextRdr;

    @ApiModelProperty("状态(0正常 1停用)")
    @TableField("status")
    private String status;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("创建者")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty("更新者")
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty("在线状态")
    @TableField("con_status")
    private String conStatus;

    @ApiModelProperty("最近一次监测时间")
    @TableField("last_time")
    private Date lastTime;

    @ApiModelProperty("sio板逻辑编号")
    @TableField("sio_number")
    private Integer sioNumber;

}
