package com.wim.aero.acs.model.scp;

import lombok.Data;

/**
 * @title: ScpShadow
 * @author: Ellie
 * @date: 2022/04/15 14:39
 * @description: 设备影子对象
 **/
@Data
public class ScpShadow {
    private int scpId;
    private ScpStatus state;    // 未初始化、已初始化、通信建立、离线

    public ScpShadow(int scpId, ScpStatus state) {
        this.scpId = scpId;
        this.state = state;
    }
}
