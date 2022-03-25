package com.wim.aero.acs.service;

import com.wim.aero.acs.db.entity.CardFormat;
import com.wim.aero.acs.db.entity.DevControllerCommonAttribute;
import com.wim.aero.acs.db.entity.DevControllerDetail;
import com.wim.aero.acs.db.service.impl.CardFormatServiceImpl;
import com.wim.aero.acs.db.service.impl.DevControllerCommonAttributeServiceImpl;
import com.wim.aero.acs.db.service.impl.DevControllerDetailServiceImpl;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.message.RequestMessage;
import com.wim.aero.acs.protocol.accessLevel.ElevatorALsSpecification;
import com.wim.aero.acs.protocol.card.AccessDatabaseSpecification;
import com.wim.aero.acs.protocol.card.MT2CardFormat;
import com.wim.aero.acs.protocol.card.WiegandCardFormat;
import com.wim.aero.acs.protocol.device.SCPDriver;
import com.wim.aero.acs.protocol.device.SCPSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @title: ScpConfigService
 * @author: Ellie
 * @date: 2022/03/23 08:53
 * @description: 控制器配置及控制器命令下发
 **/
@Slf4j
@Service
public class ScpService {
    private final DevControllerDetailServiceImpl devControllerDetailService;
    private final DevControllerCommonAttributeServiceImpl devControllerCommonAttributeService;
    private final CardFormatServiceImpl cardFormatService;

    @Autowired
    public ScpService(DevControllerDetailServiceImpl devControllerDetailService,
                      DevControllerCommonAttributeServiceImpl devControllerCommonAttributeService,
                      CardFormatServiceImpl cardFormatService) {
        this.devControllerDetailService = devControllerDetailService;
        this.devControllerCommonAttributeService = devControllerCommonAttributeService;
        this.cardFormatService = cardFormatService;
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
        DevControllerDetail detail = devControllerDetailService.getScpConfiguration(scpId);
        SCPDriver driver = SCPDriver.fromDb(detail);
        String msg = encode(scpId, driver);

    }

    private void scpSpecification(int scpId) {
        // SCPDevice Specification(Command 1107)
        SCPSpecification scpSpecification = new SCPSpecification(scpId);
        encode(scpId, scpSpecification);


        // Access Database Specification (Command 1105)
        DevControllerCommonAttribute detail = devControllerCommonAttributeService.getADSpecification();
        AccessDatabaseSpecification adSpecification = AccessDatabaseSpecification.fromDb(detail);
        encode(scpId, adSpecification);

    }

    private void elevatorScpSpecification(int scpId) {
        // Command 501: Elevator Access Level Specification
        // TODO:电梯楼层数从数据表中获取 -- 暂未定义
        ElevatorALsSpecification specification = new ElevatorALsSpecification(scpId, 64);
        encode(scpId, specification);
    }

    /**
     * 卡格式配置：所有卡格式都写入到scp中
     */
    public void cardFormatConfig(int scpId) {
        // command 1102
        List<CardFormat> list = cardFormatService.list();
        for (CardFormat item:list) {
            if (StringUtils.matchesCharacter(item.getCardType(), '0')) {   // 韦根卡
                WiegandCardFormat cardFormat = WiegandCardFormat.fromDb(item);
                encode(scpId, cardFormat);
            } else if (StringUtils.matchesCharacter(item.getCardType(), '1')) {  // 磁卡
                MT2CardFormat cardFormat = MT2CardFormat.fromDb(item);
                encode(scpId, cardFormat);
            }
        }
    }

    private String encode(int scpId, Operation operation) {
        RequestMessage message = new RequestMessage(scpId, operation);
        String msg = message.encode();

        log.info("{} 报文 {}", scpId, msg);
        return msg;
    }

}
