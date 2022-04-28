package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2022-03-22
 */
@Getter
@Setter
@TableName("defence_input")
@ApiModel(value = "DefenceInput对象", description = "")
public class DefenceInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "seq_no", type = IdType.AUTO)
    private Integer seqNo;

    @TableField("defence_id")
    private Integer defenceId;

    @TableField("defence_name")
    private String defenceName;

    @TableField("controller_id")
    private Integer controllerId;

    @TableField("input_device_id")
    private Integer inputDeviceId;

    @TableField("point_type")
    private String pointType;

    @TableField("point_num")
    private Integer pointNum;

    @TableField("status")
    private String status;

    @TableField("remark")
    private String remark;

    @TableField("scp_defence_id")
    private Integer scpDefenceId;

    @TableField("device_id")
    private Integer deviceId;

    @TableField("device_name")
    private String deviceName;

    @TableField("shield_flag")
    private String shieldFlag;

}
