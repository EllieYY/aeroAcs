package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-03-22
 */
@Getter
@Setter
@TableName("ele_level_reader")
@ApiModel(value = "EleLevelReader对象", description = "")
public class EleLevelReader implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ele_level_id")
    private Integer eleLevelId;

    @TableField("device_id")
    private Integer deviceId;

    @TableField("reader_number")
    private Integer readerNumber;

    @TableField("acr_number")
    private Integer acrNumber;

    @TableField("controller_id")
    private Integer controllerId;

    @TableField("status")
    private String status;

    @TableField("remark")
    private String remark;


}
