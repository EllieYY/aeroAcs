package com.wim.aero.acs.service;

import org.springframework.stereotype.Service;

/**
 * @title: AccessConfigService
 * @author: Ellie
 * @date: 2022/03/23 15:39
 * @description: 权限配置服务
 **/
@Service
public class AccessConfigService {
    public void AccessLevelConfig(int scpId) {
        // Command 2116: Access Level Configuration Extended
        // Command 1104: Holiday Configuration
        // command 3103
    }

    public void addCard() {
        // command 8304
    }

    public void apbConfig() {
        // command 1121
    }

    public void mpGroupConfig(int scpId) {
        // command 120
    }

    /**
     * 电梯级别配置
     * @param scpId
     */
    public void elevatorAccessLevelConfig(int scpId) {
        // command 502
    }

}
