package com.wim.aero.acs.service;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.config.DSTConfig;
import com.wim.aero.acs.db.entity.CardFormat;
import com.wim.aero.acs.db.entity.DevControllerCommonAttribute;
import com.wim.aero.acs.db.entity.DevControllerDetail;
import com.wim.aero.acs.db.service.impl.CardFormatServiceImpl;
import com.wim.aero.acs.db.service.impl.DevControllerCommonAttributeServiceImpl;
import com.wim.aero.acs.db.service.impl.DevControllerDetailServiceImpl;
import com.wim.aero.acs.message.RequestMessage;
import com.wim.aero.acs.model.DST;
import com.wim.aero.acs.model.command.CommandInfo;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.command.ScpCmdResponse;
import com.wim.aero.acs.model.mq.StatusMessage;
import com.wim.aero.acs.model.request.ScpRequestInfo;
import com.wim.aero.acs.protocol.DaylightSavingTimeConfiguration;
import com.wim.aero.acs.protocol.TimeSet;
import com.wim.aero.acs.protocol.TransactionLogSetting;
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

import java.util.*;
import java.util.stream.Collectors;

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
    private final DSTConfig dstConfig;
    private final RequestPendingCenter requestPendingCenter;
    private final QueueProducer queueProducer;

    @Autowired
    public ScpService(DevControllerDetailServiceImpl devControllerDetailService,
                      DevControllerCommonAttributeServiceImpl devControllerCommonAttributeService,
                      CardFormatServiceImpl cardFormatService,
                      RestUtil restUtil,
                      DSTConfig dstConfig,
                      RequestPendingCenter requestPendingCenter,
                      QueueProducer queueProducer) {
        this.devControllerDetailService = devControllerDetailService;
        this.devControllerCommonAttributeService = devControllerCommonAttributeService;
        this.cardFormatService = cardFormatService;
        this.restUtil = restUtil;
        this.dstConfig = dstConfig;
        this.requestPendingCenter = requestPendingCenter;
        this.queueProducer = queueProducer;
    }
    
    public void scpOnlineStateNotify(int scpId, int state) {
        StatusMessage message = new StatusMessage(
                0, System.currentTimeMillis(), scpId, Constants.mqSourceScp, scpId, 0, 0, state, Constants.mqSourceScp,"");
        queueProducer.sendStatusMessage(message);
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
     * @param requestInfo
     * @return
     */
    public void connectScp(ScpRequestInfo requestInfo) {
        int scpId = requestInfo.getScpId();

        List<ScpCmd> cmdList = new ArrayList<>();
        // Create SCP (Command 1013)
        DevControllerDetail detail = devControllerDetailService.getScpConfiguration(scpId);
        SCPDriver driver = SCPDriver.fromDb(detail);
        String msg = RequestMessage.encode(scpId, driver);
        cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));

        scpSpecification(scpId, cmdList);

        // 注册SCP
        ScpCenter.addScp(scpId);

        // 发送指令
        requestInfo.setTaskId(Constants.CONNECT_TASK_ID);
        requestPendingCenter.sendCmdList(requestInfo, cmdList);
    }


    /**
     * 获取设备连接报文
     * @return
     */
    public List<ScpCmd> getAllScpConnectMsg() {
        List<DevControllerDetail> detailList = devControllerDetailService.list();
        List<ScpCmd> cmdList = new ArrayList<>();
        for (DevControllerDetail detail:detailList) {
            int scpId = detail.getDeviceId();
            // Create SCP (Command 1013)
            SCPDriver driver = SCPDriver.fromDb(detail);
            String msg = RequestMessage.encode(scpId, driver);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }

        return cmdList;
    }

    /**
     * 控制器状态更新
     * @param scpId
     * @param status
     */
    public void scpStateUpdate(int scpId, int status) {
        // 设备状态：0 - 离线/无效  1 - 在线/正常  2 - 报警  3 - 故障 4 - 打开  5 - 关闭
        int deviceState = (status == 1) ? 1 : 0;
        StatusMessage message = new StatusMessage(
                0, System.currentTimeMillis(), scpId,
                Constants.tranSrcScpCom, scpId, Constants.customTranType, 0, deviceState, Constants.mqSourceScp,this.toString());
        queueProducer.sendStatusMessage(message);
    }

    /**
     * 控制器复位
     * @param requestInfo
     */
    public int reset(ScpRequestInfo requestInfo) {
        int scpId = requestInfo.getScpId();
        ScpReset operation = new ScpReset(scpId);
        String msg = RequestMessage.encode(scpId, operation);
        log.info("[{} - 复位] msg={}", scpId, msg);

        ScpCmd cmd = new ScpCmd(scpId, msg, IdUtil.nextId());
        return requestPendingCenter.sendCmd(requestInfo, cmd);
    }

    /**
     * 控制器重启
     * @param scpId
     * @return
     */
    public int reboot(int scpId) {

        return 0;
    }

    /**
     * 清除卡片，但不改变卡片格式
     * @param requestInfo
     * @return
     */
    public int clearCards(ScpRequestInfo requestInfo) {
        int scpId = requestInfo.getScpId();
        AccessDatabaseSpecification operation = AccessDatabaseSpecification.getCardsClearedModel(scpId);
        String msg = RequestMessage.encode(scpId, operation);
        log.info("[{} - 清除卡片] msg={}", scpId, msg);

        // 向设备发送
        ScpCmd cmd = new ScpCmd(scpId, msg, IdUtil.nextId());
        return requestPendingCenter.sendCmd(requestInfo, cmd);
    }


    /**
     * 控制器配置：定义配置流程
     * @param requestInfo
     */
    public void configScp(ScpRequestInfo requestInfo) {
        int scpId = requestInfo.getScpId();
        List<ScpCmd> cmdList = new ArrayList<>();
        scpSpecification(scpId, cmdList);

        // 303打开
        TransactionLogSetting operation = TransactionLogSetting.openLog(scpId);
        String logMsg = RequestMessage.encode(scpId, operation);
        cmdList.add(new ScpCmd(scpId, logMsg, IdUtil.nextId()));

        // 1116
        List<DST> dstList = dstConfig.getList();
        for (DST dst:dstList) {
            DaylightSavingTimeConfiguration config = new DaylightSavingTimeConfiguration(
                    scpId, dst.getStart(), dst.getEnd());
            String dstMsg = RequestMessage.encode(scpId, config);
            cmdList.add(new ScpCmd(scpId, dstMsg, IdUtil.nextId()));
        }

        // 时钟同步
        TimeSet timeSet = new TimeSet(scpId);
        String timeMsg = RequestMessage.encode(scpId, timeSet);
        cmdList.add(new ScpCmd(scpId, timeMsg, IdUtil.nextId()));

        // 有梯控则配置
//        elevatorScpSpecification(scpId, cmdList);

        // 卡格式配置
        cardFormatConfig(scpId, cmdList);

        log.info("[{} - 配置设备]", scpId);

//        for(ScpCmd cmd:cmdList) {
//            System.out.println(cmd.getCommand());
//        }

        requestPendingCenter.sendCmdList(requestInfo, cmdList);
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
        DevControllerDetail scpDetail = devControllerDetailService.getScpConfiguration(scpId);
        Map<Integer, Integer> cardIndexMap = new HashMap<>();
        if (scpDetail.getCardFormat01() != null) {
            cardIndexMap.put(0, scpDetail.getCardFormat01());
        }
        if (scpDetail.getCardFormat02() != null) {
            cardIndexMap.put(1, scpDetail.getCardFormat02());
        }
        if (scpDetail.getCardFormat03() != null) {
            cardIndexMap.put(2, scpDetail.getCardFormat03());
        }
        if (scpDetail.getCardFormat04() != null) {
            cardIndexMap.put(3, scpDetail.getCardFormat04());
        }
        if (scpDetail.getCardFormat05() != null) {
            cardIndexMap.put(4, scpDetail.getCardFormat05());
        }
        if (scpDetail.getCardFormat06() != null) {
            cardIndexMap.put(5, scpDetail.getCardFormat06());
        }
        if (scpDetail.getCardFormat07() != null) {
            cardIndexMap.put(6, scpDetail.getCardFormat07());
        }
        if (scpDetail.getCardFormat08() != null) {
            cardIndexMap.put(7, scpDetail.getCardFormat08());
        }

        List<Integer> cardIdList = cardIndexMap.values().stream().collect(Collectors.toList());
        if (cardIdList.size() <= 0) {
            return;
        }

        List<CardFormat> list = cardFormatService.getCardInfoByIdList(cardIdList);
        for (CardFormat item:list) {
            if (item.getFunctionId() == Constants.WGND) {
                WiegandCardFormat cardFormat = WiegandCardFormat.fromDb(scpId, item);
                String msg = RequestMessage.encode(scpId, cardFormat);
                // 命令组装
                cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
            } else if (item.getFunctionId() == Constants.MT2) {  // 磁卡
                MT2CardFormat cardFormat = MT2CardFormat.fromDb(scpId, item);
                String msg = RequestMessage.encode(scpId, cardFormat);
                // 命令组装
                cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
            }
        }
    }
}
