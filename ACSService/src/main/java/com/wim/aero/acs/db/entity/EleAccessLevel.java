package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2022-04-28
 */
@Getter
@Setter
@TableName("ele_access_level")
@ApiModel(value = "EleAccessLevel对象", description = "")
public class EleAccessLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ele_access_id", type = IdType.AUTO)
    private Integer eleAccessId;

    @TableField("ele_access_name")
    private String eleAccessName;

    @TableField("begin_date")
    private LocalDateTime beginDate;

    @TableField("end_date")
    private LocalDateTime endDate;

    @TableField("flag")
    private String flag;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("status")
    private String status;

    @TableField("remark")
    private String remark;


}
