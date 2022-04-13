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
@TableName("dev_reader_detail")
@ApiModel(value = "DevReaderDetail对象", description = "")
public class DevReaderDetail implements Serializable {

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

    @ApiModelProperty("上级设备类型ID")
    @TableField("p_device_type_id")
    private Integer pDeviceTypeId;

    @ApiModelProperty("上级设备ID")
    @TableField("p_device_id")
    private Integer pDeviceId;

    @ApiModelProperty("控制器ID")
    @TableField("controller_id")
    private Integer controllerId;

    @ApiModelProperty("系统区域")
    @TableField("area_id")
    private Integer areaId;

    @ApiModelProperty("名称")
    @TableField("device_name")
    private String deviceName;

    @ApiModelProperty("安装地址")
    @TableField("fix_address")
    private String fixAddress;

    @ApiModelProperty("读卡器物理编号")
    @TableField("reader_number")
    private Integer readerNumber;

    @ApiModelProperty("键盘模式")
    @TableField("key_mode")
    private Integer keyMode;

    @ApiModelProperty("读写器逻辑编号")
    @TableField("acr_number")
    private Integer acrNumber;

    @ApiModelProperty("副读卡器编号")
    @TableField("pair_acr_number")
    private Integer pairAcrNumber;

    @ApiModelProperty("读写器类型")
    @TableField("access_cfg")
    private Integer accessCfg;

    @ApiModelProperty("锁所在SIO板")
    @TableField("strk_sio")
    private Integer strkSio;

    @ApiModelProperty("锁对应控制点编号")
    @TableField("strk_number")
    private Integer strkNumber;

    @ApiModelProperty("开门时间")
    @TableField("strike_time_min")
    private Integer strikeTimeMin;

    @ApiModelProperty("关门提示时间")
    @TableField("strike_time_max")
    private Integer strikeTimeMax;

    @ApiModelProperty("门磁上电模式")
    @TableField("strike_mode")
    private Integer strikeMode;

    @ApiModelProperty("门所有SIO板")
    @TableField("door_sio")
    private Integer doorSio;

    @ApiModelProperty("门对应输入点编号")
    @TableField("door_number")
    private Integer doorNumber;

    @ApiModelProperty("开门监控时间")
    @TableField("dc_held")
    private Integer dcHeld;

    @ApiModelProperty("按键0所在接口板")
    @TableField("rex0_sio")
    private Integer rex0Sio;

    @ApiModelProperty("按键0对应输入点编号")
    @TableField("rex0_number")
    private Integer rex0Number;

    @TableField("rex1_sio")
    private Integer rex1Sio;

    @TableField("rex1_number")
    private Integer rex1Number;

    @ApiModelProperty("按键0屏蔽时间组")
    @TableField("rex0_tz")
    private Integer rex0Tz;

    @ApiModelProperty("按键1屏蔽时间组")
    @TableField("rex1_tz")
    private Integer rex1Tz;

    @ApiModelProperty("备用读写器所在SIO板")
    @TableField("altrdr_sio")
    private Integer altrdrSio;

    @ApiModelProperty("备用读写器编号")
    @TableField("altrdr_number")
    private Integer altrdrNumber;

    @ApiModelProperty("备用读写器配置")
    @TableField("altrdr_spec")
    private Integer altrdrSpec;

    @ApiModelProperty("卡格式编号")
    @TableField("cd_format")
    private Integer cdFormat;

    @ApiModelProperty("apb模式")
    @TableField("apb_mode")
    private Integer apbMode;

    @ApiModelProperty("APB延时")
    @TableField("apb_delay")
    private Integer apbDelay;

    @ApiModelProperty("APB进区域ID")
    @TableField("apb_in")
    private Integer apbIn;

    @ApiModelProperty("APB出区域ID")
    @TableField("apb_out")
    private Integer apbOut;

    @ApiModelProperty("备用")
    @TableField("spare")
    private Integer spare;

    @ApiModelProperty("访问配置")
    @TableField("actl_flags")
    private Integer actlFlags;

    @ApiModelProperty("离线模式")
    @TableField("offline_mode")
    private Integer offlineMode;

    @ApiModelProperty("在线模式")
    @TableField("default_mode")
    private Integer defaultMode;

    @ApiModelProperty("残疾人开门时间")
    @TableField("strk_t2")
    private Integer strkT2;

    @ApiModelProperty("残疾人开门报警时间")
    @TableField("dc_held2")
    private Integer dcHeld2;

    @ApiModelProperty("门特征扩展")
    @TableField("feature_type")
    private Integer featureType;

    @ApiModelProperty("过滤强行开门报警")
    @TableField("filter_alarm")
    private Integer filterAlarm;

    @ApiModelProperty("状态(0:正常 1:停用)")
    @TableField("status")
    private String status;

    @ApiModelProperty("是否考勤点")
    @TableField("kq_flag")
    private String kqFlag;

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

    @ApiModelProperty("启用开门超时报警")
    @TableField("open_over_flag")
    private String openOverFlag;

    @ApiModelProperty("报警提示语")
    @TableField("alarm_memo")
    private String alarmMemo;

    @ApiModelProperty("在线状态(0:在线 1:离线)")
    @TableField("con_status")
    private String conStatus;

    @ApiModelProperty("最近一次监测时间")
    @TableField("last_time")
    private Date lastTime;

    @ApiModelProperty("开门方向")
    @TableField("open_direction")
    private String openDirection;


}
