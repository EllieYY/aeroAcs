package com.wim.aero.acs.service;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.db.entity.Apb;
import com.wim.aero.acs.db.entity.DHoliday;
import com.wim.aero.acs.db.service.impl.*;
import com.wim.aero.acs.message.RequestMessage;
import com.wim.aero.acs.model.db.AccessLevelInfo;
import com.wim.aero.acs.model.command.CmdDownloadInfo;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.command.ScpCmdResponse;
import com.wim.aero.acs.protocol.accessLevel.AccessLevelExtended;
import com.wim.aero.acs.protocol.accessLevel.AccessLevelTest;
import com.wim.aero.acs.protocol.apb.AccessAreaConfig;
import com.wim.aero.acs.protocol.card.AccessDatabaseSpecification;
import com.wim.aero.acs.protocol.card.CardAdd;
import com.wim.aero.acs.protocol.card.CardDelete;
import com.wim.aero.acs.protocol.device.mp.MpGroupSpecification;
import com.wim.aero.acs.protocol.timezone.Holiday;
import com.wim.aero.acs.protocol.timezone.TimeZone;
import com.wim.aero.acs.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    public AccessConfigService(DHolidayServiceImpl holidayService,
                               DSchedulesGroupDetailServiceImpl schedulesGroupService,
                               ApbServiceImpl apbService, DefenceInputServiceImpl defenceInputService,
                               DAccessLevelDoorServiceImpl accessLevelService,
                               CCardInfoServiceImpl cardInfoService, RestUtil restUtil) {
        this.holidayService = holidayService;
        this.schedulesGroupService = schedulesGroupService;
        this.apbService = apbService;
        this.defenceInputService = defenceInputService;
        this.accessLevelService = accessLevelService;
        this.cardInfoService = cardInfoService;
        this.restUtil = restUtil;
    }

    /**
     * 权限访问 - 相关配置
     * @param scpId
     * @param cmdList
     */
    public void alBasicConfig(int scpId, List<ScpCmd> cmdList) {
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
    public List<CmdDownloadInfo> downloadCards(int scpId) {
        List<CardAdd> cardAddList = cardInfoService.getByScpId(scpId);

        return packageCardMessages(cardAddList);
    }


    /**
     * 添加卡片
     * @param cards
     * @return 发送失败的结果
     */
    public List<CmdDownloadInfo> addCards(List<String> cards) {
        List<CardAdd> cardAddList = cardInfoService.getByCardList(cards);
        return packageCardMessages(cardAddList);
    }

    /**
     * 删除卡片
     * @param cardList
     * @return 发送失败的结果
     */
    public List<CmdDownloadInfo> deleteCards(int scpId, List<String> cardList) {
        List<ScpCmd> cmdList = new ArrayList<>();
        for (String cardNo:cardList) {
            CardDelete operation = new CardDelete(scpId, cardNo);
            String msg = RequestMessage.encode(scpId, operation);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }
//        AccessDatabaseSpecification operation = AccessDatabaseSpecification.getCardsClearedModel(scpId);
//        String msg = RequestMessage.encode(scpId, operation);
//
//        log.info("[SCP]清除卡片 clear cards: scpId={}, msg={}", scpId, msg);
//
//        // 向设备发送
//        ScpCmdResponse response = restUtil.sendSingleCmd(new ScpCmd(scpId, msg, IdUtil.nextId()));
//        log.info("清除卡片，[{}] - [{}]:[{}]", scpId, response.getCode(), response.getReason());
//
//        return response.getCode();
//
//        List<CardAdd> cardAddList = cardInfoService.getByCardNo(cards);
//        return packageCardMessages(cardAddList);

        return null;
    }

    /**
     * 报文打包
     * @param cardAddList
     * @return 发送失败的结果
     */
    public List<CmdDownloadInfo> packageCardMessages(List<CardAdd> cardAddList) {
        Map<String, CmdDownloadInfo> resultMap = new HashMap<>();
        // command 8304
        List<ScpCmd> cmdList = new ArrayList<>();
        for(CardAdd item:cardAddList) {
            item.alListFix();

            int scpId = item.getScpNumber();
            String msg = RequestMessage.encode(scpId, item);
            String streamId = IdUtil.nextId();
            cmdList.add(new ScpCmd(scpId, msg, streamId));

            // TODO:
//            resultMap.put(streamId, new CmdDownloadInfo(scpId, msg, streamId, 0, item.getCardNumber()));
        }

        // 下发到控制器
        log.info(cmdList.toString());
        List<CmdDownloadInfo> resultList = new ArrayList<>();
        List<ScpCmdResponse> responseList = restUtil.sendMultiCmd(cmdList);
        for (ScpCmdResponse response:responseList) {
            int code = response.getCode();
            String streamId = response.getStreamId();
            if (code == Constants.REST_CODE_SUCCESS) {
                resultMap.remove(streamId);
            } else {
                if (!resultMap.containsKey(streamId)) {
                    log.error("sendMultiCmd未返回响应，{}", streamId);
                    continue;
                }
                CmdDownloadInfo info = resultMap.get(streamId);
                info.setCode(code);
                info.setReason(response.getReason());
                resultList.add(info);
            }
        }

        return resultList;
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
        List<TimeZone> list = schedulesGroupService.getTimeZones(scpId);
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
