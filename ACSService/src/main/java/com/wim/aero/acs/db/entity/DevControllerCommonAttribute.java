package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("dev_controller_common_attribute")
@ApiModel(value = "DevControllerCommonAttribute对象", description = "")
public class DevControllerCommonAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("auto_id")
    private Integer autoId;

    @TableField("max_card_num")
    private Integer maxCardNum;

    @TableField("apb_schedules_group_id")
    private Integer apbSchedulesGroupId;

    @TableField("max_card_length")
    private Integer maxCardLength;

    @TableField("max_pin")
    private Integer maxPin;

    @TableField("additional_flag")
    private Integer additionalFlag;

    @TableField("force_alarm_mode")
    private String forceAlarmMode;

    @TableField("force_alarm_offset")
    private Integer forceAlarmOffset;

    @TableField("enable_times_flag")
    private String enableTimesFlag;

    @TableField("additional_access_flag")
    private String additionalAccessFlag;

    @TableField("apb_control_flag")
    private String apbControlFlag;

    @TableField("apb_time_flag")
    private String apbTimeFlag;

    @TableField("card_invalid_flag")
    private String cardInvalidFlag;

    @TableField("card_save_flag")
    private String cardSaveFlag;

    @TableField("save_vacation_flag")
    private String saveVacationFlag;

    @TableField("save_temp_flag")
    private String saveTempFlag;

    @TableField("strong_pwd_flag")
    private String strongPwdFlag;

    @TableField("elevator_floor_num")
    private Integer elevatorFloorNum;

    @TableField("access_level_num")
    private Integer accessLevelNum;

    @TableField("host_over_time")
    private Integer hostOverTime;

    @TableField("sec_card_over_time")
    private Integer secCardOverTime;

    @TableField("mor_card_over_time")
    private Integer morCardOverTime;

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

    @TableField("b_user_level")
    private Integer bUserLevel;


}
