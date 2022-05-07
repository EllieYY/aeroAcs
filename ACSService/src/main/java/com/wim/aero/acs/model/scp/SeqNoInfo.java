package com.wim.aero.acs.model.scp;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.sql.DataSourceDefinitions;

/**
 * @title: SeqNoInfo
 * @author: Ellie
 * @date: 2022/05/07 11:11
 * @description:
 **/
@Data
@AllArgsConstructor
public class SeqNoInfo {
    private int scpId;
    private long extractStart;   // 提取范围最小值
    private long extractEnd;     // 提取范围最大值
    private long extractMax;     // 提取事件时最大值
}
