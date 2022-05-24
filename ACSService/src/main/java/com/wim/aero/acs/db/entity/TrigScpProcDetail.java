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
 * @since 2022-05-23
 */
@Getter
@Setter
@TableName("trig_scp_proc_detail")
@ApiModel(value = "TrigScpProcDetail对象", description = "")
public class TrigScpProcDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "seq_no", type = IdType.AUTO)
    private Integer seqNo;

    @TableField("controller_id")
    private Integer controllerId;

    @TableField("proc_id")
    private Integer procId;

    @TableField("proc_name")
    private String procName;

    @TableField("prefix")
    private Integer prefix;

    @TableField("function_id")
    private Integer functionId;

    @TableField("para_01")
    private Integer para01;

    @TableField("para_02")
    private Integer para02;

    @TableField("para_03")
    private Integer para03;

    @TableField("para_04")
    private Integer para04;

    @TableField("para_05")
    private Integer para05;

    @TableField("para_06")
    private Integer para06;

    @TableField("order_num")
    private Integer orderNum;


}
