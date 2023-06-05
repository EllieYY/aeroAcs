package com.wim.aero.acs.util.ProtocolFiledUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

/**
 * @title: AcsCmdPropParam
 * @author: Ellie
 * @date: 2022/02/16 15:56
 * @description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmdPropParam implements Comparable<CmdPropParam>{
    private Integer idx;

    private String defaultVal;



    private String codec;

    private Field field;

    @Override
    public int compareTo(CmdPropParam o) {
        return this.idx.compareTo(o.idx);
    }
}
