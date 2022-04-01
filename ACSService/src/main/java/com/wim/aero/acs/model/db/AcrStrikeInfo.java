package com.wim.aero.acs.model.db;

import lombok.Data;

/**
 * @title: AcrStrikeInfo
 * @author: Ellie
 * @date: 2022/03/30 16:00
 * @description:
 **/
@Data
public class AcrStrikeInfo {
    private int acrDeviceId;   // 主键
    private int scpId;
    private int strikeId;    // 控制点编号 - 逻辑编号
}
