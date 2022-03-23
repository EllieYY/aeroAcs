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
@TableName("d_access_level")
@ApiModel(value = "DAccessLevel对象", description = "")
public class DAccessLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("access_level_id")
    private Integer accessLevelId;

    @TableField("access_level_name")
    private String accessLevelName;

    @TableField("begin_date")
    private LocalDateTime beginDate;

    @TableField("end_date")
    private LocalDateTime endDate;

    @TableField("flag")
    private String flag;

    @TableField("status")
    private String status;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private LocalDateTime updateTime;


}
