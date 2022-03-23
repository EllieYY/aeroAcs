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
@TableName("card_format")
@ApiModel(value = "CardFormat对象", description = "")
public class CardFormat implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("seq_no")
    private Integer seqNo;

    @TableField("card_type")
    private String cardType;

    @TableField("type_no")
    private Integer typeNo;

    @TableField("facility")
    private Integer facility;

    @TableField("offset")
    private Integer offset;

    @TableField("function_id")
    private Integer functionId;

    @TableField("min_digits")
    private Integer minDigits;

    @TableField("max_digits")
    private Integer maxDigits;

    @TableField("bits")
    private Integer bits;

    @TableField("pe_ln")
    private Integer peLn;

    @TableField("po_ln")
    private Integer poLn;

    @TableField("fc_ln")
    private Integer fcLn;

    @TableField("fc_loc")
    private Integer fcLoc;

    @TableField("ch_ln")
    private Integer chLn;

    @TableField("ch_loc")
    private Integer chLoc;


}
