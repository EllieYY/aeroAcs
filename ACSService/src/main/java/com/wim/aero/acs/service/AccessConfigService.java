package com.wim.aero.acs.service;

import com.wim.aero.acs.db.entity.Apb;
import com.wim.aero.acs.db.entity.DHoliday;
import com.wim.aero.acs.db.service.impl.*;
import com.wim.aero.acs.message.RequestMessage;
import com.wim.aero.acs.model.db.AccessLevelInfo;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.db.EleAccessLevelInfo;
import com.wim.aero.acs.model.request.CardBlockedRequestInfo;
import com.wim.aero.acs.model.request.CardRequestInfo;
import com.wim.aero.acs.model.request.ScpRequestInfo;
import com.wim.aero.acs.model.request.TaskRequest;
import com.wim.aero.acs.protocol.accessLevel.AccessLevelException;
import com.wim.aero.acs.protocol.accessLevel.AccessLevelExtended;
import com.wim.aero.acs.protocol.accessLevel.AccessLevelTest;
import com.wim.aero.acs.protocol.accessLevel.ElevatorALsConfiguration;
import com.wim.aero.acs.protocol.apb.AccessAreaConfig;
import com.wim.aero.acs.protocol.card.CardAdd;
import com.wim.aero.acs.protocol.card.CardDelete;
import com.wim.aero.acs.protocol.device.mp.MpGroupSpecification;
import com.wim.aero.acs.protocol.timezone.Holiday;
import com.wim.aero.acs.protocol.timezone.TimeZone;
import com.wim.aero.acs.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: AccessConfigService
 * @author: Ellie
 * @date: 2022/03/23 15:39
 * @description: 权限配置服务
 **/
@Slf4j
@Service
public class AccessConfigService {

    private final DHolidayServiceImpl holidayService;
    private final DSchedulesGroupDetailServiceImpl schedulesGroupService;
    private final ApbServiceImpl apbService;
    private final DefenceInputServiceImpl defenceInputService;
    private final DAccessLevelDoorServiceImpl accessLevelService;
    private final CCardInfoServiceImpl cardInfoService;
    private final RequestPendingCenter requestPendingCenter;
    private final DevControllerDetailServiceImpl controllerDetailService;
    @Autowired
    public AccessConfigService(DHolidayServiceImpl holidayService,
                               DSchedulesGroupDetailServiceImpl schedulesGroupService,
                               ApbServiceImpl apbService, DefenceInputServiceImpl defenceInputService,
                               DAccessLevelDoorServiceImpl accessLevelService,
                               CCardInfoServiceImpl cardInfoService,
                               RequestPendingCenter requestPendingCenter,
                               DevControllerDetailServiceImpl controllerDetailService) {
        this.holidayService = holidayService;
        this.schedulesGroupService = schedulesGroupService;
        this.apbService = apbService;
        this.defenceInputService = defenceInputService;
        this.accessLevelService = accessLevelService;
        this.cardInfoService = cardInfoService;
        this.requestPendingCenter = requestPendingCenter;
        this.controllerDetailService = controllerDetailService;
    }

    public void accessConfig(ScpRequestInfo requestInfo) {
        int scpId = requestInfo.getScpId();

        List<ScpCmd> cmdList = new ArrayList<>();
        alBasicConfigMsg(scpId, cmdList);

//        for(ScpCmd cmd:cmdList) {
//            log.info(cmd.getCommand());
//        }

        requestPendingCenter.sendCmdList(requestInfo, cmdList);
    }

    /**
     * 权限访问 - 相关配置
     * @param scpId
     * @param cmdList
     */
    public void alBasicConfigMsg(int scpId, List<ScpCmd> cmdList) {
        boolean isEleScp = controllerDetailService.isEleScp(scpId);
        addHolidays(scpId, cmdList);
        addTimeZone(scpId, isEleScp, cmdList);
        elevatorAccessLevelConfig(scpId, isEleScp, cmdList);
        accessLevelConfig(scpId, isEleScp, cmdList);

        apbConfig(scpId, cmdList);
        mpGroupConfig(scpId, cmdList);
    }

    /**
     * 下载卡片
     * @param scpId
     */
    @Async
    public void downloadCards(TaskRequest request, int scpId) {
        List<CardAdd> cardAddList = cardInfoService.getByScpId(scpId);
        List<ScpCmd> cmdList = packageCardMessages(cardAddList);
        log.info("[{} - 卡片下载] {}", scpId, cmdList);

        // 下发到控制器
        requestPendingCenter.sendCmdList(request, cmdList);
    }


    /**
     * 添加卡片
     * @param
     * @return 发送失败的结果
     */
    @Async
    public void addCards(CardRequestInfo request) {
        List<String> cardList = request.getCardList();

        List<CardAdd> cardAddList = cardInfoService.getByCardList(cardList);
        List<ScpCmd> cmdList = packageCardMessages(cardAddList);

        // 下发到控制器
        requestPendingCenter.sendCmdList(request, cmdList);
    }

    /**
     * 删除卡片
     * @param
     * @return 发送失败的结果
     */
    public void deleteCards(CardRequestInfo request) {
        List<String> cardList = request.getCardList();

        // 查找拥有这张卡的控制器
        List<Integer> scpIdList = cardInfoService.getScpIdsByCardNo(cardList);

        // 组织报文
        List<ScpCmd> cmdList = new ArrayList<>();
        for (Integer scpId:scpIdList) {
            for (String cardNo : cardList) {
                CardDelete operation = new CardDelete(scpId, cardNo);
                String msg = RequestMessage.encode(scpId, operation);
                cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
            }
        }

        // 下发到控制器
        requestPendingCenter.sendCmdList(request, cmdList);
    }

    /**
     * 卡冻结解冻
     * @param request
     */
    public void cardBlocked(CardBlockedRequestInfo request) {
        List<String> cardList = request.getCardList();
        // 查找拥有这张卡的控制器
        List<Integer> scpIdList = cardInfoService.getScpIdsByCardNo(cardList);

        // 组织报文
        List<ScpCmd> cmdList = new ArrayList<>();
        for (Integer scpId:scpIdList) {
            for (String cardNo : cardList) {
                AccessLevelException operation = new AccessLevelException(scpId, cardNo, request.getTz(), request.isBlocked());
                String msg = RequestMessage.encode(scpId, operation);
                cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
            }
        }

        // 下发到控制器
        requestPendingCenter.sendCmdList(request, cmdList);
    }

    /**
     * 报文打包
     * @param cardAddList
     * @return
     */
    public List<ScpCmd> packageCardMessages(List<CardAdd> cardAddList) {
        // command 8304
        List<ScpCmd> cmdList = new ArrayList<>();
        for(CardAdd item:cardAddList) {
            item.alListFix();

            int scpId = item.getScpNumber();
            String msg = RequestMessage.encode(scpId, item);
            String streamId = IdUtil.nextId();
            cmdList.add(new ScpCmd(scpId, msg, streamId));

            log.info(item.toString());
        }

        return cmdList;
    }

    /**
     * 访问级别配置
     * @param scpId
     * @param cmdList
     */
    private void accessLevelConfig(int scpId, boolean isEleScp, List<ScpCmd> cmdList) {
        List<AccessLevelInfo> list = new ArrayList<>();
        if (isEleScp) {
            list = accessLevelService.getByScpIdForEle(scpId);
        } else {
            list = accessLevelService.getByScpId(scpId);
        }
        for(AccessLevelInfo item:list) {
            // Command 124
            AccessLevelTest alTest = AccessLevelTest.fromDb(item);
            String alTestMsg = RequestMessage.encode(scpId, alTest);
            cmdList.add(new ScpCmd(scpId, alTestMsg, IdUtil.nextId()));

            // Command 2116: Access Level Configuration Extended
            AccessLevelExtended alExtended = AccessLevelExtended.fromDb(item);
            String alExtendedMsg = RequestMessage.encode(scpId, alExtended);
            cmdList.add(new ScpCmd(scpId, alExtendedMsg, IdUtil.nextId()));
        }
    }

    /**
     * 假日配置
     * @param scpId
     * @param cmdList
     */
    private void addHolidays(int scpId, List<ScpCmd> cmdList) {
        // Command 1104: Holiday Configuration
        List<DHoliday> list = holidayService.list();
        for(DHoliday holiday:list) {
            Holiday config = Holiday.fronDb(scpId, holiday);
            String configMsg = RequestMessage.encode(scpId, config);
            cmdList.add(new ScpCmd(scpId, configMsg, IdUtil.nextId()));
        }
    }

    /**
     * 时间组配置
     * @param scpId
     * @param cmdList
     */
    public void addTimeZone(int scpId, boolean isEleScp, List<ScpCmd> cmdList) {
        // command 3103
        List<TimeZone> list = new ArrayList<>();
        if (isEleScp) {
            list = schedulesGroupService.getTimeZonesForEleScp(scpId);
        } else {
            list = schedulesGroupService.getTimeZonesByScp(scpId);
        }
        for(TimeZone item:list) {
            item.updateIntervalSize();
            String msg = RequestMessage.encode(scpId, item);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }
    }

    /**
     * apb配置
     * @param scpId
     * @param cmdList
     */
    private void apbConfig(int scpId, List<ScpCmd> cmdList) {
        // command 1121
        List<Apb> list = apbService.getByScpId(scpId);
        for(Apb item:list) {
            AccessAreaConfig config = AccessAreaConfig.fromDb(item);
            String configMsg = RequestMessage.encode(scpId, config);
            cmdList.add(new ScpCmd(scpId, configMsg, IdUtil.nextId()));
        }
    }

    /**
     * 防区配置
     * @param scpId
     * @param cmdList
     */
    private void mpGroupConfig(int scpId, List<ScpCmd> cmdList) {
        // command 120
        List<MpGroupSpecification> list = defenceInputService.getByScpId(scpId);
        for(MpGroupSpecification item:list) {
            item.updateMpCount();
            String msg = RequestMessage.encode(scpId, item);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }
    }

    /**
     * 电梯级别配置
     * @param scpId
     */
    private void elevatorAccessLevelConfig(int scpId, boolean isEleScp, List<ScpCmd> cmdList) {
        if (!isEleScp) {
            return;
        }

        // command 502
        List<EleAccessLevelInfo> list = accessLevelService.getEleLevelByScp(scpId);
        for(EleAccessLevelInfo item:list) {
            ElevatorALsConfiguration config = ElevatorALsConfiguration.fromDb(item);
            String alTestMsg = RequestMessage.encode(scpId, config);
            cmdList.add(new ScpCmd(scpId, alTestMsg, IdUtil.nextId()));
        }
    }

}
