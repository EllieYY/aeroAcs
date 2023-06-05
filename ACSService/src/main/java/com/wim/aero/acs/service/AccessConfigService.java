package com.wim.aero.acs.service;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.db.entity.Apb;
import com.wim.aero.acs.db.entity.DHoliday;
import com.wim.aero.acs.db.service.impl.*;
import com.wim.aero.acs.message.RequestMessage;
import com.wim.aero.acs.model.db.AccessLevelInfo;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.db.EleAccessLevelInfo;
import com.wim.aero.acs.model.request.*;
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
import com.wim.aero.acs.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public void accessConfig(ScpRequestInfo requestInfo, List<ScpCmd> cmdList) {
        int scpId = requestInfo.getScpId();

//        List<ScpCmd> cmdList = new ArrayList<>();
        alBasicConfigMsg(scpId, cmdList);

        log.info("[{} - 权限信息配置]", scpId);
//        for(ScpCmd cmd:cmdList) {
//            log.info(cmd.getCommand());
//        }

//        requestPendingCenter.sendCmdList(requestInfo, cmdList);
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
        // 梯控兼容
        boolean isEleScp = controllerDetailService.isEleScp(scpId);
        List<CardAdd> cardAddList = new ArrayList<>();
        if (isEleScp) {
            cardAddList = cardInfoService.getByEleScpId(scpId);
        } else {
            cardAddList = cardInfoService.getByScpId(scpId);
        }

        packageCardMessages(request, cardAddList);
    }


    /**
     * 添加卡片
     * @param
     * @return 发送失败的结果
     */
    @Async
    public void addCards(CardRequestInfo request) {
        log.info("授权参数：{}", request.toString());
        boolean isEleScp = request.isEleScp();

        List<String> cardList = request.getCardList();
        log.info("梯控标识：{} - 加卡 {}", isEleScp, cardList.toString());

        // 卡数量控制
        List<List<String>> batchCardList = StringUtil.fixedGrouping(cardList, Constants.BATCH_CARD_DATA_COUNT);
        for (List<String> batchCard : batchCardList) {
            if (batchCard.size() <= 0) {
                continue;
            }

            int scpId = request.getScpId();
            log.info("[batch card] {} - {}:{}", scpId, batchCard.size(), batchCard.toString());

            List<CardAdd> cardAddList = new ArrayList<>();
            if (scpId == 0) {
                try {
                    if (isEleScp) {
                        cardAddList = cardInfoService.getByCardListForEleScp(batchCard);
                    } else {
                        cardAddList = cardInfoService.getByCardList(batchCard);
                    }
                } catch (DataAccessException e) {
                    log.error("[授权关系查询出错batch card] {}", batchCard.toString());
                    log.error(e.toString());
                }
            } else {
//            List<Integer> scpIdList = new ArrayList<>();
//            if (isEleScp) {
//                scpIdList = cardInfoService.getScpIdsByCardNoForEle(batchCard);
//            } else {
//                scpIdList = cardInfoService.getScpIdsByCardNo(batchCard);
//            }
//
//            for (Integer scpId : scpIdList) {
//                log.info("[batch card] {} - {}:{}", scpId, batchCard.size(), batchCard.toString());
                //添加冻结状态判断 梯控
                try {
                    if (isEleScp) {
                        cardAddList = cardInfoService.getByCardListAndScpIdForEleScp(scpId, batchCard);
                    } else {
                        cardAddList = cardInfoService.getByCardListAndScpId(scpId, batchCard);
                    }
                } catch (DataAccessException e) {
                    log.error("[控制器授权关系查询出错batch card] {}-{}", scpId, batchCard.toString());
                    log.error(e.toString());
                }
            }

            packageCardMessages(request, cardAddList);

        }

    }

    /**
     * 删除卡片
     * @param
     * @return 发送失败的结果
     */
    public void deleteCards(CardListDeleteRequest request) {
        List<CardDeleteInfo> cardList = request.getCardDelList();

        List<ScpCmd> cmdList = new ArrayList<>();
        for (CardDeleteInfo item:cardList) {
            String cardNo = item.getCardNo();
            List<Integer> scpIdList = item.getScpIdList();
            for (Integer scpId : scpIdList) {
                CardDelete operation = new CardDelete(scpId, cardNo);
                String msg = RequestMessage.encode(scpId, operation);

                ScpCmd cmd = new ScpCmd(scpId, msg, IdUtil.nextId());
                cmd.setType(Constants.SCP_CMD_CARD_DEL);
                cmd.setCardNo(cardNo);
                cmd.setAlvlListStr("0");

                cmdList.add(cmd);
            }
        }

        log.info("删卡：{}", cmdList.toString());
        // 下发到控制器
        requestPendingCenter.sendCmdList(request, cmdList);
    }

    /**
     * 卡冻结解冻
     * TODO：有问题，对于
     * @param request
     */
    public void cardBlocked(CardBlockedRequestInfo request) {
        String cardNo = request.getCardNo();
        List<String> cardList = Arrays.asList(cardNo);
        // 查找拥有这张卡的控制器
        List<Integer> scpIdList = cardInfoService.getScpIdsByCardNo(cardList);

        // TODO:梯控冻结
        List<Integer> eleScpList = cardInfoService.getScpIdsByCardNoForEle(cardList);
        scpIdList.addAll(eleScpList);


        // 组织报文
        List<ScpCmd> cmdList = new ArrayList<>();
        for (Integer scpId:scpIdList) {
            AccessLevelException operation = new AccessLevelException(scpId, cardNo, request.getReaderIdList());
            String msg = RequestMessage.encode(scpId, operation);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }

        log.info("卡冻结-卡挂失 {}", cmdList.toString());

        // 下发到控制器
        requestPendingCenter.sendCmdList(request, cmdList);
    }

    /**
     * 报文打包
     * @param cardAddList
     * @return
     */
    public void packageCardMessages(TaskRequest request, List<CardAdd> cardAddList) {
        if (cardAddList.size() <= 0) {
            return;
        }

        List<List<CardAdd>> batchCardList = StringUtil.fixedGrouping(cardAddList, Constants.BATCH_CARD_COUNT);
        for (List<CardAdd> batchCardAdd:batchCardList) {
            // command 8304
            List<ScpCmd> cmdList = new ArrayList<>();
            for (CardAdd item : batchCardAdd) {
                item.alListFix();

                int scpId = item.getScpNumber();
                if (scpId == 0) {
                    continue;
                }
                String msg = RequestMessage.encode(scpId, item);
                ScpCmd cmd = new ScpCmd(scpId, msg, IdUtil.nextId());
                cmd.setType(Constants.SCP_CMD_CARD_ADD);
                cmd.setCardNo(item.getCardNumber());

                // 访问级别列表去重，逗号分割
                List<Integer> alvlList = item.getAlvl().stream().distinct().collect(Collectors.toList());
                cmd.setAlvlListStr(StringUtils.join(alvlList, ","));

                cmdList.add(cmd);

//            log.info(item.toString());
            }

            log.info("添加卡片 {}", cmdList.size());
            // 下发到控制器
            requestPendingCenter.sendCmdList(request, cmdList);
        }

//        return cmdList;
    }

    /**
     * 控制器访问级别更新
     * @param
     * @param
     */
    public void accessLevelConfig(AlvlRequestInfo request) {
        // 组织报文
        List<ScpCmd> cmdList = new ArrayList<>();

        // 下载时间组
        addTimeZone(request.getScpId(), request.isEle(), cmdList);

        // 下载访问级别
        accessLevelConfig(request.getScpId(), request.isEle(), cmdList);

        log.info("[scp全部访问级别更新] - {}", cmdList.toString());
        // 下发到控制器
        requestPendingCenter.sendCmdList(request, cmdList);
    }

    /**
     * 控制器指定访问级别更新
     * @param
     * @param
     */
    public void accessLevelListConfig(AlvlListRequestInfo request) {
//        int scpId = request.getScpId();
        boolean isEleScp = request.isEle();
        List<Integer> alvList = request.getAlvList();
        if (alvList.size() <= 0) {
            return;
        }

        List<ScpCmd> cmdList = new ArrayList<>();
        List<AccessLevelInfo> list = new ArrayList<>();
        if (isEleScp) {
            list = accessLevelService.getAlvListForEle(alvList);
        } else {
            list = accessLevelService.getAlvList(alvList);
        }
        for(AccessLevelInfo item:list) {
            int scpId = item.getNScpNumber();
            // Command 124
            AccessLevelTest alTest = AccessLevelTest.fromDb(item);
            String alTestMsg = RequestMessage.encode(scpId, alTest);
            cmdList.add(new ScpCmd(scpId, alTestMsg, IdUtil.nextId()));

            // Command 2116: Access Level Configuration Extended
            AccessLevelExtended alExtended = AccessLevelExtended.fromDb(item);
            String alExtendedMsg = RequestMessage.encode(scpId, alExtended);
            cmdList.add(new ScpCmd(scpId, alExtendedMsg, IdUtil.nextId()));
        }

        log.info("[scp部分访问级别更新] - {}", cmdList.toString());
        // 下发到控制器
        requestPendingCenter.sendCmdList(request, cmdList);
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
        List<TimeZone> list = schedulesGroupService.getAllTimeZonesForScp(scpId);
//        if (isEleScp) {
//            list = schedulesGroupService.getTimeZonesForEleScp(scpId);
//        } else {
//            list = schedulesGroupService.getTimeZonesByScp(scpId);
//        }
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
