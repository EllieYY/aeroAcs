package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("ele_level_detail")
@ApiModel(value = "EleLevelDetail对象", description = "")
public class EleLevelDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ele_level_id")
    private Integer eleLevelId;

    @TableField("ele_floor_id")
    private Integer eleFloorId;

    @TableField("ele_floor_name")
    private String eleFloorName;

    @TableField("schedules_group_id")
    private Integer schedulesGroupId;

    @TableField("status")
    private String status;

    @TableField("remark")
    private String remark;


}
