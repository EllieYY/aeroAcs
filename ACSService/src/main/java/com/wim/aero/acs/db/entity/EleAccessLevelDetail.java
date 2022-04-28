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
@TableName("ele_access_level_detail")
@ApiModel(value = "EleAccessLevelDetail对象", description = "")
public class EleAccessLevelDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("ele_access_id")
    private Integer eleAccessId;

    @TableField("device_id")
    private Integer deviceId;

    @TableField("device_name")
    private String deviceName;

    @TableField("reader_number")
    private Integer readerNumber;

    @TableField("acr_number")
    private Integer acrNumber;

    @TableField("controller_id")
    private Integer controllerId;

    @TableId("ele_level_id")
    private Integer eleLevelId;

    @TableField("status")
    private String status;

    @TableField("remark")
    private String remark;


}
