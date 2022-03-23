package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Date;
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
 * @since 2022-03-22
 */
@Getter
@Setter
@TableName("dev_x_detail")
@ApiModel(value = "DevXDetail对象", description = "")
public class DevXDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("device_id")
    private Integer deviceId;

    @TableField("device_type_id")
    private Integer deviceTypeId;

    @TableField("device_scope_id")
    private Integer deviceScopeId;

    @TableField("controller_id")
    private Integer controllerId;

    @TableField("device_name")
    private String deviceName;

    @TableField("controller_port")
    private Integer controllerPort;

    @TableField("address")
    private Integer address;

    @TableField("model")
    private Integer model;

    @TableField("sio_next_in")
    private Integer sioNextIn;

    @TableField("sio_next_out")
    private Integer sioNextOut;

    @TableField("sio_next_rdr")
    private Integer sioNextRdr;

    @TableField("status")
    private String status;

    @TableField("remark")
    private String remark;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private Date updateTime;


}
