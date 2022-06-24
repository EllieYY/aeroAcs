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
 * @since 2022-06-22
 */
@Getter
@Setter
@TableName("trigger_var")
@ApiModel(value = "TriggerVar对象", description = "")
public class TriggerVar implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("seq_no")
    private Integer seqNo;

    @TableField("controller_id")
    private Integer controllerId;

    @TableField("var_id")
    private Integer varId;

    @TableField("var_name")
    private String varName;

    @TableField("var_value")
    private Integer varValue;


}
