package com.wim.aero.acs.service;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.db.entity.CardFormat;
import com.wim.aero.acs.db.entity.DevControllerCommonAttribute;
import com.wim.aero.acs.db.entity.DevControllerDetail;
import com.wim.aero.acs.db.service.impl.CardFormatServiceImpl;
import com.wim.aero.acs.db.service.impl.DevControllerCommonAttributeServiceImpl;
import com.wim.aero.acs.db.service.impl.DevControllerDetailServiceImpl;
import com.wim.aero.acs.message.RequestMessage;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.command.ScpCmdResponse;
import com.wim.aero.acs.protocol.accessLevel.ElevatorALsSpecification;
import com.wim.aero.acs.protocol.card.AccessDatabaseSpecification;
import com.wim.aero.acs.protocol.card.MT2CardFormat;
import com.wim.aero.acs.protocol.card.WiegandCardFormat;
import com.wim.aero.acs.protocol.device.SCPDriver;
import com.wim.aero.acs.protocol.device.SCPSpecification;
import com.wim.aero.acs.protocol.device.ScpReset;
import com.wim.aero.acs.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
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
    private final RestUtil restUtil;

    @Autowired
    public ScpService(DevControllerDetailServiceImpl devControllerDetailService,
                      DevControllerCommonAttributeServiceImpl devControllerCommonAttributeService,
                      CardFormatServiceImpl cardFormatService, RestUtil restUtil) {
        this.devControllerDetailService = devControllerDetailService;
        this.devControllerCommonAttributeService = devControllerCommonAttributeService;
        this.cardFormatService = cardFormatService;
        this.restUtil = restUtil;
    }

    /**
     * 控制器合法性判断
     * @param scpId
     * @return
     */
    public boolean isValidScpId(int scpId) {
        return devControllerDetailService.validScp(scpId);
    }

    /**
     * 控制器通信连接
     * @param scpId
     * @return
     */
    public ScpCmd connectScp(int scpId) {
        // Create SCP (Command 1013)
        DevControllerDetail detail = devControllerDetailService.getScpConfiguration(scpId);
        SCPDriver driver = SCPDriver.fromDb(detail);
        String msg = RequestMessage.encode(scpId, driver);

        return new ScpCmd(scpId, msg, IdUtil.nextId());
    }

    /**
     * 控制器复位
     * @param scpId
     */
    public int reset(int scpId) {
        ScpReset operation = new ScpReset(scpId);
        String msg = RequestMessage.encode(scpId, operation);

        log.info("[SCP]复位 reset: scpId={}, msg={}", scpId, msg);

        // 向设备发送
        ScpCmdResponse response = restUtil.sendSingleCmd(new ScpCmd(scpId, msg, IdUtil.nextId()));

        log.info("SCP复位失败，[{}] - [{}]:[{}]", scpId, response.getCode(), response.getReason());

        return response.getCode();
    }

    /**
     * 清除卡片，但不改变卡片格式
     * @param scpId
     * @return
     */
    public int clearCards(int scpId) {
        AccessDatabaseSpecification operation = AccessDatabaseSpecification.getCardsClearedModel(scpId);
        String msg = RequestMessage.encode(scpId, operation);

        log.info("[SCP]清除卡片 clear cards: scpId={}, msg={}", scpId, msg);

        // 向设备发送
        ScpCmdResponse response = restUtil.sendSingleCmd(new ScpCmd(scpId, msg, IdUtil.nextId()));
        log.info("清除卡片，[{}] - [{}]:[{}]", scpId, response.getCode(), response.getReason());

        return response.getCode();
    }




    /**
     * 控制器配置：定义配置流程
     * @param scpId
     */
    public List<ScpCmd> configScp(int scpId) {
        List<ScpCmd> cmdList = new ArrayList<>();
        scpSpecification(scpId, cmdList);

        // 有梯控则配置
        elevatorScpSpecification(scpId, cmdList);

        // 卡格式配置
        cardFormatConfig(scpId, cmdList);

        return cmdList;
    }


    /**
     * scp规格及数据库配置
     * @param scpId
     * @param cmdList
     */
    private void scpSpecification(int scpId, List<ScpCmd> cmdList) {
        // SCPDevice Specification(Command 1107)
        SCPSpecification scpSpecification = new SCPSpecification(scpId);
        String specificationMsg = RequestMessage.encode(scpId, scpSpecification);

        // Access Database Specification (Command 1105)
        DevControllerCommonAttribute detail = devControllerCommonAttributeService.getADSpecification();
        AccessDatabaseSpecification adSpecification = AccessDatabaseSpecification.fromDb(scpId, detail);
        String adSpecificationMsg = RequestMessage.encode(scpId, adSpecification);

        // 命令组装
        cmdList.add(new ScpCmd(scpId, specificationMsg, IdUtil.nextId()));
        cmdList.add(new ScpCmd(scpId, adSpecificationMsg, IdUtil.nextId()));
    }

    /**
     * 梯控SCP配置
     * @param scpId
     * @param cmdList
     */
    private void elevatorScpSpecification(int scpId, List<ScpCmd> cmdList) {
        // Command 501: Elevator Access Level Specification
        // TODO:电梯楼层数从数据表中获取 -- 暂未定义
        ElevatorALsSpecification specification = new ElevatorALsSpecification(scpId, 64);
        String msg = RequestMessage.encode(scpId, specification);

        // 命令组装
        cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
    }

    /**
     * 卡格式配置：所有卡格式都写入到scp中
     */
    private void cardFormatConfig(int scpId, List<ScpCmd> cmdList) {
        // command 1102
        List<CardFormat> list = cardFormatService.list();
        for (CardFormat item:list) {
            if (StringUtils.matchesCharacter(item.getCardType(), '0')) {   // 韦根卡
                WiegandCardFormat cardFormat = WiegandCardFormat.fromDb(scpId, item);
                String msg = RequestMessage.encode(scpId, cardFormat);
                // 命令组装
                cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
            } else if (StringUtils.matchesCharacter(item.getCardType(), '1')) {  // 磁卡
                MT2CardFormat cardFormat = MT2CardFormat.fromDb(scpId, item);
                String msg = RequestMessage.encode(scpId, cardFormat);
                // 命令组装
                cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
            }
        }
    }



}
