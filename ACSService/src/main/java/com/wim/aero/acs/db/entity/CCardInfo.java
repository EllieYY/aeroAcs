package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("c_card_info")
@ApiModel(value = "CCardInfo对象", description = "")
public class CCardInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("card_no")
    private String cardNo;

    @TableField("card_seq")
    private Integer cardSeq;

    @TableField("pin")
    private String pin;

    @TableField("card_type")
    private String cardType;

    @TableField("effect_begin_time")
    private String effectBeginTime;

    @TableField("effect_end_time")
    private String effectEndTime;

    @TableField("holiday_begin_time")
    private String holidayBeginTime;

    @TableField("holiday_end_time")
    private String holidayEndTime;

    @TableField("temp_upgrade_begin_time")
    private String tempUpgradeBeginTime;

    @TableField("temp_upgrade_end_time")
    private String tempUpgradeEndTime;

    @TableField("times")
    private Integer times;

    @TableField("apb_flag")
    private String apbFlag;

    @TableField("additional_flag")
    private String additionalFlag;

    @TableField("disabled_flag")
    private String disabledFlag;

    @TableField("card_pwd_flag")
    private String cardPwdFlag;

    @TableField("state")
    private String state;

    @TableField("first_card_flag")
    private String firstCardFlag;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("apb_id")
    private Integer apbId;


}
