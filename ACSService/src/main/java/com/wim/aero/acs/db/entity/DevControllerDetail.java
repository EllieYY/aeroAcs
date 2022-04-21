package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ellie
 * @since 2022-03-24
 */
@Getter
@Setter
@TableName("dev_controller_detail")
@ApiModel(value = "DevControllerDetail对象", description = "")
public class DevControllerDetail implements Serializable {

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

    @ApiModelProperty("名称")
    @TableField("device_name")
    private String deviceName;

    @ApiModelProperty("设备IP")
    @TableField("device_ip")
    private String deviceIp;

    @ApiModelProperty("MAC地址")
    @TableField("device_mac")
    private String deviceMac;

    @ApiModelProperty("端口号")
    @TableField("device_port")
    private Integer devicePort;

    @ApiModelProperty("连接类型")
    @TableField("connect_type")
    private Integer connectType;

    @ApiModelProperty("通讯间隔")
    @TableField("cc_interval")
    private Integer ccInterval;

    @ApiModelProperty("通行尝试次数")
    @TableField("cc_times")
    private Integer ccTimes;

    @ApiModelProperty("帧等候时间")
    @TableField("wait_time")
    private Integer waitTime;

    @ApiModelProperty("密码")
    @TableField("pwd")
    private String pwd;

    @ApiModelProperty("安装地址")
    @TableField("fix_address")
    private String fixAddress;

    @ApiModelProperty("状态(0:启用 1:停用)")
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

    @ApiModelProperty("在线状态(0:在线 1:离线)")
    @TableField("con_status")
    private String conStatus;

    @ApiModelProperty("最近一次监测时间")
    @TableField("last_time")
    private Date lastTime;

    @TableField("card_format_01")
    private Integer cardFormat01;

    @TableField("card_format_02")
    private Integer cardFormat02;

    @TableField("card_format_03")
    private Integer cardFormat03;

    @TableField("card_format_04")
    private Integer cardFormat04;

    @TableField("card_format_05")
    private Integer cardFormat05;

    @TableField("card_format_06")
    private Integer cardFormat06;

    @TableField("card_format_07")
    private Integer cardFormat07;

    @TableField("card_format_08")
    private Integer cardFormat08;
}
