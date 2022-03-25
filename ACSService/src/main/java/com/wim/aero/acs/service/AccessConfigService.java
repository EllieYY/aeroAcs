package com.wim.aero.acs.service;

import com.wim.aero.acs.db.entity.Apb;
import com.wim.aero.acs.db.entity.DHoliday;
import com.wim.aero.acs.db.service.impl.*;
import com.wim.aero.acs.message.RequestMessage;
import com.wim.aero.acs.model.AccessLevelInfo;
import com.wim.aero.acs.protocol.accessLevel.AccessLevelExtended;
import com.wim.aero.acs.protocol.accessLevel.AccessLevelTest;
import com.wim.aero.acs.protocol.apb.AccessAreaConfig;
import com.wim.aero.acs.protocol.device.MpGroupSpecification;
import com.wim.aero.acs.protocol.timezone.Holiday;
import com.wim.aero.acs.protocol.timezone.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title: AccessConfigService
 * @author: Ellie
 * @date: 2022/03/23 15:39
 * @description: 权限配置服务
 **/
@Service
public class AccessConfigService {

    private final DHolidayServiceImpl holidayService;
    private final DSchedulesGroupDetailServiceImpl schedulesGroupService;
    private final ApbServiceImpl apbService;
    private final DefenceInputServiceImpl defenceInputService;
    private final DAccessLevelDoorServiceImpl accessLevelService;
    @Autowired
    public AccessConfigService(DHolidayServiceImpl holidayService,
                               DSchedulesGroupDetailServiceImpl schedulesGroupService,
                               ApbServiceImpl apbService, DefenceInputServiceImpl defenceInputService,
                               DAccessLevelDoorServiceImpl accessLevelService) {
        this.holidayService = holidayService;
        this.schedulesGroupService = schedulesGroupService;
        this.apbService = apbService;
        this.defenceInputService = defenceInputService;
        this.accessLevelService = accessLevelService;
    }


    public void AccessLevelConfig(int scpId) {
        // Command 2116: Access Level Configuration Extended
        // Command 124
        List<AccessLevelInfo> list = accessLevelService.getByScpId(scpId);
        for(AccessLevelInfo item:list) {
            AccessLevelTest alTest = AccessLevelTest.fromDb(item);
            // TODO:组报文


            AccessLevelExtended alExtended = AccessLevelExtended.fromDb(item);
            // TODO:组报文


        }
    }

    // 所有假日下到控制器中
    private void addHolidays(int scpId) {
        // Command 1104: Holiday Configuration
        List<DHoliday> list = holidayService.list();
        for(DHoliday holiday:list) {
            Holiday config = Holiday.fronDb(scpId, holiday);
            // TODO:组报文

        }
    }

    private void addTimeZone(int scpId) {
        // command 3103
        List<TimeZone> list = schedulesGroupService.getTimeZones(scpId);
        for(TimeZone item:list) {
            item.updateIntervalSize();
            // TODO:组报文
            RequestMessage.encode(scpId, item);
        }
    }

    public void addCard() {
        // command 8304
    }

    public void apbConfig(int scpId) {
        // command 1121
        List<Apb> list = apbService.getByScpId(scpId);
        for(Apb item:list) {
            AccessAreaConfig config = AccessAreaConfig.fromDb(item);
            // TODO:组报文
        }
    }

    public void mpGroupConfig(int scpId) {
        // command 120
        List<MpGroupSpecification> list = defenceInputService.getByScpId(scpId);
        for(MpGroupSpecification item:list) {
            item.updateMpCount();
            // TODO:组报文
            RequestMessage.encode(scpId, item);

        }
    }

    /**
     * 电梯级别配置
     * @param scpId
     */
    public void elevatorAccessLevelConfig(int scpId) {
        // command 502
    }

}
