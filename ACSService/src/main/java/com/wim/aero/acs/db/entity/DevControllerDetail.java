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
@TableName("dev_controller_detail")
@ApiModel(value = "DevControllerDetail对象", description = "")
public class DevControllerDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("device_id")
    private Integer deviceId;

    @TableField("device_type_id")
    private Integer deviceTypeId;

    @TableField("device_scope_id")
    private Integer deviceScopeId;

    @TableField("device_name")
    private String deviceName;

    @TableField("device_ip")
    private String deviceIp;

    @TableField("device_mac")
    private String deviceMac;

    @TableField("device_port")
    private Integer devicePort;

    @TableField("connect_type")
    private Integer connectType;

    @TableField("cc_interval")
    private Integer ccInterval;

    @TableField("cc_times")
    private Integer ccTimes;

    @TableField("wait_time")
    private Integer waitTime;

    @TableField("pwd")
    private String pwd;

    @TableField("fix_address")
    private String fixAddress;

    @TableField("b_user_level")
    private Integer bUserLevel;

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
