package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("apb")
@ApiModel(value = "Apb对象", description = "")
public class Apb implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("apb_id")
    private Integer apbId;

    @TableField("apb_name")
    private String apbName;

    @TableField("controller_id")
    private Integer controllerId;

    @TableField("curr_num_person")
    private Integer currNumPerson;

    @TableField("max_num_person")
    private Integer maxNumPerson;

    @TableField("max_num_event")
    private Integer maxNumEvent;

    @TableField("min_num_event")
    private Integer minNumEvent;

    @TableField("close_flag")
    private String closeFlag;

    @TableField("apb_rule")
    private String apbRule;

    @TableField("init_area")
    private String initArea;

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


}
