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
@TableName("d_holiday")
@ApiModel(value = "DHoliday对象", description = "")
public class DHoliday implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("holiday_id")
    private Integer holidayId;

    @TableField("holiday_name")
    private String holidayName;

    @TableField("holiday_type")
    private String holidayType;

    @TableField("begin_date")
    private LocalDateTime beginDate;

    @TableField("continued_day")
    private Integer continuedDay;

    @TableField("status")
    private String status;

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


}
