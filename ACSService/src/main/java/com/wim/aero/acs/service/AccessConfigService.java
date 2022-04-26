package com.wim.aero.acs.service;

import com.wim.aero.acs.db.entity.Apb;
import com.wim.aero.acs.db.entity.DHoliday;
import com.wim.aero.acs.db.service.impl.*;
import com.wim.aero.acs.message.RequestMessage;
import com.wim.aero.acs.model.db.AccessLevelInfo;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.command.ScpCmdResponse;
import com.wim.aero.acs.model.request.CardBlockedRequestInfo;
import com.wim.aero.acs.model.request.CardRequestInfo;
import com.wim.aero.acs.model.request.ScpRequestInfo;
import com.wim.aero.acs.protocol.accessLevel.AccessLevelException;
import com.wim.aero.acs.protocol.accessLevel.AccessLevelExtended;
import com.wim.aero.acs.protocol.accessLevel.AccessLevelTest;
import com.wim.aero.acs.protocol.apb.AccessAreaConfig;
import com.wim.aero.acs.protocol.card.CardAdd;
import com.wim.aero.acs.protocol.card.CardDelete;
import com.wim.aero.acs.protocol.device.mp.MpGroupSpecification;
import com.wim.aero.acs.protocol.timezone.Holiday;
import com.wim.aero.acs.protocol.timezone.TimeZone;
import com.wim.aero.acs.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
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
    private final RestUtil restUtil;
    private final RequestPendingCenter requestPendingCenter;
    @Autowired
    public AccessConfigService(DHolidayServiceImpl holidayService,
                               DSchedulesGroupDetailServiceImpl schedulesGroupService,
                               ApbServiceImpl apbService, DefenceInputServiceImpl defenceInputService,
                               DAccessLevelDoorServiceImpl accessLevelService,
                               CCardInfoServiceImpl cardInfoService,
                               RestUtil restUtil,
                               RequestPendingCenter requestPendingCenter) {
        this.holidayService = holidayService;
        this.schedulesGroupService = schedulesGroupService;
        this.apbService = apbService;
        this.defenceInputService = defenceInputService;
        this.accessLevelService = accessLevelService;
        this.cardInfoService = cardInfoService;
        this.restUtil = restUtil;
        this.requestPendingCenter = requestPendingCenter;
    }

    public void accessConfig(ScpRequestInfo requestInfo) {
        int scpId = requestInfo.getScpId();

        List<ScpCmd> cmdList = new ArrayList<>();

        alBasicConfigMsg(scpId, cmdList);

        for(ScpCmd cmd:cmdList) {
            log.info(cmd.getCommand());
        }

        // TODO:优化
        requestPendingCenter.add(requestInfo.getTaskId(), requestInfo.getTaskName(), requestInfo.getTaskSource(), cmdList);
        List<ScpCmdResponse> responseList = restUtil.sendMultiCmd(cmdList);
        requestPendingCenter.updateSeq(scpId, responseList);
    }

    /**
     * 权限访问 - 相关配置
     * @param scpId
     * @param cmdList
     */
    public void alBasicConfigMsg(int scpId, List<ScpCmd> cmdList) {
        addHolidays(scpId, cmdList);
        addTimeZone(scpId, cmdList);
        accessLevelConfig(scpId, cmdList);

        apbConfig(scpId, cmdList);
        mpGroupConfig(scpId, cmdList);
    }

    /**
     * 下载卡片
     * @param scpId
     */
    public void downloadCards(long taskId, String taskName, int taskSource, int scpId) {
        List<CardAdd> cardAddList = cardInfoService.getByScpId(scpId);
        List<ScpCmd> cmdList = packageCardMessages(cardAddList);

        // 下发到控制器
        requestPendingCenter.add(taskId, taskName, taskSource, cmdList);
        List<ScpCmdResponse> responseList = restUtil.sendMultiCmd(cmdList);
        requestPendingCenter.updateSeq(scpId, responseList);
    }


    /**
     * 添加卡片
     * @param
     * @return 发送失败的结果
     */
    public void addCards(CardRequestInfo requestInfo) {
        List<String> cardList = requestInfo.getCardList();

        List<CardAdd> cardAddList = cardInfoService.getByCardList(cardList);
        List<ScpCmd> cmdList = packageCardMessages(cardAddList);

        // 下发到控制器
        requestPendingCenter.add(requestInfo.getTaskId(),
                requestInfo.getTaskName(),
                requestInfo.getTaskSource(),
                cmdList);
        List<ScpCmdResponse> responseList = restUtil.sendMultiCmd(cmdList);
        // TODO:改造
        requestPendingCenter.updateSeq(0, responseList);
    }

    /**
     * 删除卡片
     * @param
     * @return 发送失败的结果
     */
    public void deleteCards(CardRequestInfo requestInfo) {
        List<String> cardList = requestInfo.getCardList();

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
        requestPendingCenter.add(requestInfo.getTaskId(),
                requestInfo.getTaskName(),
                requestInfo.getTaskSource(),
                cmdList);
        List<ScpCmdResponse> responseList = restUtil.sendMultiCmd(cmdList);
        // 需要改造
        requestPendingCenter.updateSeq(0, responseList);
    }

    /**
     * 卡冻结解冻
     * @param requestInfo
     */
    public void cardBlocked(CardBlockedRequestInfo requestInfo) {
        List<String> cardList = requestInfo.getCardList();
        // 查找拥有这张卡的控制器
        List<Integer> scpIdList = cardInfoService.getScpIdsByCardNo(cardList);

        // 组织报文
        List<ScpCmd> cmdList = new ArrayList<>();
        for (Integer scpId:scpIdList) {
            for (String cardNo : cardList) {
                AccessLevelException operation = new AccessLevelException(scpId, cardNo, requestInfo.getTz(), requestInfo.isBlocked());
                String msg = RequestMessage.encode(scpId, operation);
                cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
            }
        }

        // 下发到控制器
        requestPendingCenter.add(requestInfo.getTaskId(),
                requestInfo.getTaskName(),
                requestInfo.getTaskSource(),
                cmdList);
        List<ScpCmdResponse> responseList = restUtil.sendMultiCmd(cmdList);
        // TODO:需要改造
        requestPendingCenter.updateSeq(0, responseList);
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
        }

        return cmdList;
    }

    /**
     * 访问级别配置
     * @param scpId
     * @param cmdList
     */
    private void accessLevelConfig(int scpId, List<ScpCmd> cmdList) {
        // Command 2116: Access Level Configuration Extended
        // Command 124
        List<AccessLevelInfo> list = accessLevelService.getByScpId(scpId);
        for(AccessLevelInfo item:list) {
            AccessLevelTest alTest = AccessLevelTest.fromDb(item);
            String alTestMsg = RequestMessage.encode(scpId, alTest);
            cmdList.add(new ScpCmd(scpId, alTestMsg, IdUtil.nextId()));

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
    public void addTimeZone(int scpId, List<ScpCmd> cmdList) {
        // command 3103
        List<TimeZone> list = schedulesGroupService.getTimeZonesByScp(scpId);
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
    private void elevatorAccessLevelConfig(int scpId, List<ScpCmd> cmdList) {
        // command 502
    }

}
