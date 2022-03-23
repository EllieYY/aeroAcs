package com.wim.aero.acs.service;

import org.springframework.stereotype.Service;

/**
 * @title: SIOService
 * @author: Ellie
 * @date: 2022/03/23 15:38
 * @description: SIO板配置及读卡器、报警点、控制点远程控制命令下发
 **/
@Service
public class SIOService {

    public void configSio(int scpId, int sio) {
        // MSP1(SIO)Comm. Driver Configuration (Command 108) -- // 一个控制器两个
        // SIOPanel Configuration (Command 109) -- 报警板配置，不同类型不同配置

    }

    public void inputConfig(int scpId, int sio) {
        // Input Point Configuration (Command 110)
        // Monitor Point Configuration(Command113)

    }

    public void outputConfig(int scpId, int sio) {
        // OutputPointConfiguration (Command 111)
        // ControlPointConfiguration (Command 114)

    }

    public void readerConfig(int scpId, int sio) {
        // Card Reader Configuration(Command112)
        // Access Control Reader Configuration(Command115)

    }
}
