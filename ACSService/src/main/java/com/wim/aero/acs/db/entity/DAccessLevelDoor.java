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
@TableName("d_access_level_door")
@ApiModel(value = "DAccessLevelDoor对象", description = "")
public class DAccessLevelDoor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("access_level_id")
    private Integer accessLevelId;

    @TableField("access_level_name")
    private String accessLevelName;

    @TableField("schedules_group_id")
    private Integer schedulesGroupId;

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


}
