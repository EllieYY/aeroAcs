package com.wim.aero.acs.db.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ellie
 * @since 2022-08-01
 */
@Getter
@Setter
@TableName("d_employee_auth")
@Data
@ApiModel(value = "DEmployeeAuth对象", description = "")
public class DEmployeeAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "seq_no", type = IdType.AUTO)
    private Integer seqNo;

    @TableField("card_no")
    private String cardNo;

    @TableField("controller_id")
    private Integer controllerId;

    @TableField("access_level_id_list")
    private String accessLevelIdList;

    @TableField("start_time")
    private Date startTime;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField("status")
    private String status;

    @TableField("detail")
    private String detail;

    @TableField("message")
    private String message;


}
