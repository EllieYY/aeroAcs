package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
@TableName("d_schedules")
@ApiModel(value = "DSchedules对象", description = "")
public class DSchedules implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("schedules_id")
    private Integer schedulesId;

    @TableField("schedules_name")
    private String schedulesName;

    @TableField("begin_time")
    private LocalTime beginTime;

    @TableField("end_time")
    private LocalTime endTime;

    @TableField("mon_flag")
    private String monFlag;

    @TableField("tues_flag")
    private String tuesFlag;

    @TableField("wed_flag")
    private String wedFlag;

    @TableField("thu_flag")
    private String thuFlag;

    @TableField("fri_flag")
    private String friFlag;

    @TableField("sat_flag")
    private String satFlag;

    @TableField("sun_flag")
    private String sunFlag;

    @TableField("status")
    private String status;

    @TableField("remark")
    private String remark;

    @TableField("holiday_type_1")
    private String holidayType1;

    @TableField("holiday_type_2")
    private String holidayType2;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private LocalDateTime updateTime;


}
