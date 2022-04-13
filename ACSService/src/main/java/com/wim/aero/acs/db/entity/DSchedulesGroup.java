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
@TableName("d_schedules_group")
@ApiModel(value = "DSchedulesGroup对象", description = "")
public class DSchedulesGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("schedules_group_id")
    private Integer schedulesGroupId;

    @TableField("status")
    private String status;

    @TableField("remark")
    private String remark;

    @TableField("schedules_group_name")
    private String schedulesGroupName;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private Date updateTime;


}
