package com.wim.aero.acs.protocol.accessLevel;

import lombok.Data;

/**
 * @title: ModifyAccessAreaConfig
 * @author: Ellie
 * @date: 2022/03/15 14:30
 * @description: 访问区域参数修改
 **/
@Data
public class ModifyAccessAreaConfig {
    private int scpNumber;
    private int areaNumber;   // 1~127

    /**
     * 1 = Disable area
     * 2 = Enable area
     * 3 = Set current occupancy to occ_set value
     * 4 = Reserved, do not use
     * 5 = Clear occupancy counts of the “standard” and “special” users
     *     Multi-occupancy mode control:
     * 6 = Disable multi-occupancy rules
     * 7 = Enable standard multi-occupancy processing
     * 8 = Reserved, do not use
     * 9 = Reserved, do not use
     */
    private int command;
    private int occSet;
}
