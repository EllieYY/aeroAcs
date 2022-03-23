package com.wim.aero.acs.service;

import com.wim.aero.acs.db.entity.DevControllerDetail;
import com.wim.aero.acs.db.service.DevControllerDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @title: ScpConfigService
 * @author: Ellie
 * @date: 2022/03/23 08:53
 * @description: 控制器配置及控制器命令下发
 **/
@Service
public class ScpService {
    private final DevControllerDetailServiceImpl devControllerDetailService;

    @Autowired
    public ScpService(DevControllerDetailServiceImpl devControllerDetailService) {
        this.devControllerDetailService = devControllerDetailService;
    }

    /**
     * 定义配置流程
     * @param scpId
     */
    public void configScp(int scpId) {
        createScp(scpId);
        scpSpecification(scpId);

        // 有梯控则配置
        elevatorScpSpecification(scpId);

        // scp连接成功后，检查配置信息，重新发送 scpSpecification();

    }




    private void createScp(int scpId) {
        // Create SCP (Command 1013)
//        DevControllerDetail devControllerDetail = devControllerDetailService;



    }

    private void scpSpecification(int scpId) {
        // SCPDevice Specification(Command 1107)
        // Access Database Specification (Command 1105)


    }

    private void elevatorScpSpecification(int scpId) {
        // Command 501: Elevator Access Level Specification

    }

    /**
     * 卡格式配置
     */
    public void cardFormatConfig(int scpId) {
        // command 1102
    }

}
