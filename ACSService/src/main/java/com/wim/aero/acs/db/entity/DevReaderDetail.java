package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("dev_reader_detail")
@ApiModel(value = "DevReaderDetail对象", description = "")
public class DevReaderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("device_id")
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

    @TableField("area_id")
    private Integer areaId;

    @TableField("device_name")
    private String deviceName;

    @TableField("fix_address")
    private String fixAddress;

    @TableField("reader_number")
    private Integer readerNumber;

    @TableField("key_mode")
    private Integer keyMode;

    @TableField("acr_number")
    private Integer acrNumber;

    @TableField("pair_acr_number")
    private Integer pairAcrNumber;

    @TableField("access_cfg")
    private Integer accessCfg;

    @TableField("strk_sio")
    private Integer strkSio;

    @TableField("strk_number")
    private Integer strkNumber;

    @TableField("strike_time_min")
    private Integer strikeTimeMin;

    @TableField("strike_time_max")
    private Integer strikeTimeMax;

    @TableField("strike_mode")
    private Integer strikeMode;

    @TableField("door_sio")
    private Integer doorSio;

    @TableField("door_number")
    private Integer doorNumber;

    @TableField("dc_held")
    private Integer dcHeld;

    @TableField("rex0_sio")
    private Integer rex0Sio;

    @TableField("rex0_number")
    private Integer rex0Number;

    @TableField("rex1_sio")
    private Integer rex1Sio;

    @TableField("rex1_number")
    private Integer rex1Number;

    @TableField("rex0_tz")
    private Integer rex0Tz;

    @TableField("rex1_tz")
    private Integer rex1Tz;

    @TableField("altrdr_sio")
    private Integer altrdrSio;

    @TableField("altrdr_number")
    private Integer altrdrNumber;

    @TableField("altrdr_spec")
    private Integer altrdrSpec;

    @TableField("cd_format")
    private Integer cdFormat;

    @TableField("apb_mode")
    private Integer apbMode;

    @TableField("apb_delay")
    private Integer apbDelay;

    @TableField("apb_in")
    private Integer apbIn;

    @TableField("apb_out")
    private Integer apbOut;

    @TableField("spare")
    private Integer spare;

    @TableField("actl_flags")
    private Integer actlFlags;

    @TableField("offline_mode")
    private Integer offlineMode;

    @TableField("default_mode")
    private Integer defaultMode;

    @TableField("strk_t2")
    private Integer strkT2;

    @TableField("dc_held2")
    private Integer dcHeld2;

    @TableField("feature_type")
    private Integer featureType;

    @TableField("filter_alarm")
    private Integer filterAlarm;

    @TableField("status")
    private String status;

    @TableField("kq_flag")
    private String kqFlag;

    @TableField("remark")
    private String remark;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("open_over_flag")
    private String openOverFlag;

    @TableField("alarm_memo")
    private String alarmMemo;


}
