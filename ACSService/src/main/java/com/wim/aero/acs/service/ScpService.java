package com.wim.aero.acs.service;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.config.DSTConfig;
import com.wim.aero.acs.config.ScpSpecificationConfig;
import com.wim.aero.acs.db.entity.*;
import com.wim.aero.acs.db.service.impl.*;
import com.wim.aero.acs.message.RequestMessage;
import com.wim.aero.acs.model.DST;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.db.TriggerInfoEx;
import com.wim.aero.acs.model.mq.StatusMessage;
import com.wim.aero.acs.model.request.*;
import com.wim.aero.acs.protocol.DaylightSavingTimeConfiguration;
import com.wim.aero.acs.protocol.TimeSet;
import com.wim.aero.acs.protocol.TransactionLogSetting;
import com.wim.aero.acs.protocol.accessLevel.ElevatorALsSpecification;
import com.wim.aero.acs.protocol.card.AccessDatabaseSpecification;
import com.wim.aero.acs.protocol.card.MT2CardFormat;
import com.wim.aero.acs.protocol.card.WiegandCardFormat;
import com.wim.aero.acs.protocol.device.*;
import com.wim.aero.acs.protocol.device.cp.ControlPointStatusCommand;
import com.wim.aero.acs.protocol.device.mp.MonitorPointStatusCommand;
import com.wim.aero.acs.protocol.device.reader.AcrStatusCommand;
import com.wim.aero.acs.protocol.trigger.ActionSpecification;
import com.wim.aero.acs.protocol.trigger.ProcedureControl;
import com.wim.aero.acs.protocol.trigger.TriggerSpecificationExtend;
import com.wim.aero.acs.protocol.trigger.TriggerVariableControl;
import com.wim.aero.acs.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    private final TrigScpProcDetailServiceImpl trigScpProcDetailService;
    private final TriggerInfoServiceImpl triggerInfoService;
    private final DevControllerDetailServiceImpl controllerDetailService;
    private final ScpSpecificationConfig scpSpecificationConfig;
    private final TriggerVarServiceImpl triggerVarService;
    private final SioService sioService;
    private final AccessConfigService accessConfigService;

    @Autowired
    public ScpService(DevControllerDetailServiceImpl devControllerDetailService,
                      DevControllerCommonAttributeServiceImpl devControllerCommonAttributeService,
                      CardFormatServiceImpl cardFormatService,
                      RestUtil restUtil,
                      DSTConfig dstConfig,
                      RequestPendingCenter requestPendingCenter,
                      QueueProducer queueProducer,
                      TrigScpProcDetailServiceImpl trigScpProcDetailService,
                      TriggerInfoServiceImpl triggerInfoService,
                      DevControllerDetailServiceImpl controllerDetailService,
                      ScpSpecificationConfig scpSpecificationConfig,
                      TriggerVarServiceImpl triggerVarService,
                      SioService sioService,
                      AccessConfigService accessConfigService) {
        this.devControllerDetailService = devControllerDetailService;
        this.devControllerCommonAttributeService = devControllerCommonAttributeService;
        this.cardFormatService = cardFormatService;
        this.restUtil = restUtil;
        this.dstConfig = dstConfig;
        this.requestPendingCenter = requestPendingCenter;
        this.queueProducer = queueProducer;
        this.trigScpProcDetailService = trigScpProcDetailService;
        this.triggerInfoService = triggerInfoService;
        this.controllerDetailService = controllerDetailService;
        this.scpSpecificationConfig = scpSpecificationConfig;
        this.triggerVarService = triggerVarService;
        this.sioService = sioService;
        this.accessConfigService = accessConfigService;
    }
    
    public void scpOnlineStateNotify(int scpId, int state) {
        StatusMessage message = new StatusMessage(
                0, System.currentTimeMillis(), scpId, Constants.TRAN_TABLE_SRC_SCP,
                scpId, 0, 0, state,
                Constants.TRAN_TABLE_SRC_SCP,"");
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
//        requestInfo.setTaskId(Constants.CONNECT_TASK_ID);
        requestPendingCenter.sendCmdList(requestInfo, cmdList);
    }


    /**
     * 控制器删除
     * @param requestInfo
     * @return
     */
    public int deleteScp(ScpRequestInfo requestInfo) {
        int scpId = requestInfo.getScpId();

        // 注销scp
        ScpCenter.deleteScp(scpId);

        ScpDelete operaton = new ScpDelete(scpId);
        String msg = RequestMessage.encode(scpId, operaton);
        log.info("[{} - 控制器删除] msg={}", scpId, msg);

        ScpCmd cmd = new ScpCmd(scpId, msg, IdUtil.nextId());
        return requestPendingCenter.sendCmd(requestInfo, cmd);
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
        String srcStr = "通信服务上报控制器状态：" + scpId + ":" + status;
        // 设备状态：0 - 离线/无效  1 - 在线/正常  2 - 报警  3 - 故障 4 - 打开  5 - 关闭
        int deviceState = (status == 1) ? 1 : 0;
        StatusMessage message = new StatusMessage(
                0, System.currentTimeMillis(), scpId,
                Constants.tranSrcScpCom, scpId, Constants.customTranType, 0,
                deviceState, Constants.TRAN_TABLE_SRC_SCP, srcStr);
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
        DevControllerCommonAttribute detail = devControllerCommonAttributeService.getADSpecification();
        detail.setMaxCardNum(0);
        AccessDatabaseSpecification operation = AccessDatabaseSpecification.fromDb(scpId, detail);

//        AccessDatabaseSpecification operation = AccessDatabaseSpecification.getCardsClearedModel(scpId);
        String msg = RequestMessage.encode(scpId, operation);
        log.info("[{} - 清除卡片] msg={}", scpId, msg);

        // 向设备发送
        ScpCmd cmd = new ScpCmd(scpId, msg, IdUtil.nextId());
        return requestPendingCenter.sendCmd(requestInfo, cmd);
    }


    /**
     * 事件提取
     * @param requestInfo
     * @return
     */
    public int eventExtraction(TransactionRequestInfo requestInfo) {
        int scpId = requestInfo.getScpId();
        long eventIndex = requestInfo.getEventStartNo();
        TransactionLogSetting operation = new TransactionLogSetting(scpId, eventIndex);
        String msg = RequestMessage.encode(scpId, operation);
        log.info("[{} - 事件提取] msg={}", scpId, msg);

        // 向设备发送
        ScpCmd cmd = new ScpCmd(scpId, msg, IdUtil.nextId());
        return requestPendingCenter.sendCmd(requestInfo, cmd);
    }

    /**
     * 执行过程
     * @param request
     * @return
     */
    public int procedureExecute(ProcedureCommandRequest request) {
        int scpId = request.getScpId();

        ProcedureControl operation = new ProcedureControl(scpId, request.getProcedureId(), request.getPrefix());
        String msg = RequestMessage.encode(scpId, operation);
        log.info("[{} - 执行过程] msg={}", scpId, msg);

        // 向设备发送
        ScpCmd cmd = new ScpCmd(scpId, msg, IdUtil.nextId());
        return requestPendingCenter.sendCmd(request, cmd);
    }

    /**
     * 卡容量查询
     * @param request
     */
    public int cardAmountRequest(ScpCardsRequest request) {
        List<ScpCmd> cmdList = new ArrayList<>();
        for (Integer scpId:request.getScpList()) {
            ScpIDRequest operation = new ScpIDRequest(scpId);
            String msg = RequestMessage.encode(scpId, operation);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }

        log.info("[卡容量查询] - {}", cmdList.toString());
        return requestPendingCenter.sendCmdList(request, cmdList);
    }

    /**
     * 设备状态请求
     * @param request
     * @return
     */
    public void requestDeviceStatus(DeviceStatusRequest request) {
        List<ScpDeviceInfo> scpDeviceInfoList = request.getDeviceList();
        for (ScpDeviceInfo deviceInfo:scpDeviceInfoList) {
            List<ScpCmd> cmdList = new ArrayList<>();

            int scpId = deviceInfo.getScpId();
            sioStatusCmds(scpId, deviceInfo.getSioList(), cmdList);
            cpStatusCmds(scpId, deviceInfo.getCpList(), cmdList);
            mpStatusCmds(scpId, deviceInfo.getMpList(), cmdList);
            acrStatusCmds(scpId, deviceInfo.getAcrList(), cmdList);

            log.info("[设备状态查询] - {}", cmdList.toString());

            requestPendingCenter.sendCmdList(request, cmdList);
        }
    }

    private void sioStatusCmds(int scpId, List<Integer> sioList, List<ScpCmd> cmdList) {
        for (Integer sioId:sioList) {
            ControlPointStatusCommand operation = new ControlPointStatusCommand(scpId, sioId, 1);
            String msg = RequestMessage.encode(scpId, operation);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }
    }

    private void cpStatusCmds(int scpId, List<Integer> cpList, List<ScpCmd> cmdList) {
        for (Integer cpId:cpList) {
            ControlPointStatusCommand operation = new ControlPointStatusCommand(scpId, cpId, 1);
            String msg = RequestMessage.encode(scpId, operation);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }
    }

    private void mpStatusCmds(int scpId, List<Integer> mpList, List<ScpCmd> cmdList) {
        for (Integer mpId:mpList) {
            MonitorPointStatusCommand operation = new MonitorPointStatusCommand(scpId, mpId, 1);
            String msg = RequestMessage.encode(scpId, operation);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }
    }

    private void acrStatusCmds(int scpId, List<Integer> acrList, List<ScpCmd> cmdList) {
        for (Integer acrId:acrList) {
            AcrStatusCommand operation = new AcrStatusCommand(scpId, acrId, 1);
            String msg = RequestMessage.encode(scpId, operation);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }
    }


    /**
     * 控制器配置：定义配置流程
     * @param requestInfo
     */
    @Async
    public void configScp(ScpRequestInfo requestInfo, List<ScpCmd> cmdList) {
        log.info(" taskId = {}", requestInfo.getTaskId());

        int scpId = requestInfo.getScpId();
//        List<ScpCmd> cmdList = new ArrayList<>();
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

        // 时钟同步 Command 302: Time Set
        TimeSet timeSet = new TimeSet(scpId);
        String timeMsg = RequestMessage.encode(scpId, timeSet);
        cmdList.add(new ScpCmd(scpId, timeMsg, IdUtil.nextId()));

        // 卡格式配置 1102
        cardFormatConfig(scpId, cmdList);

        // 触发器配置
        triggerConfig(scpId, cmdList);

        log.info("[{} - scp配置]", scpId);

        // 访问权限配置
        accessConfigService.accessConfig(requestInfo, cmdList);

        // 硬件配置
        sioService.configSioForScp(requestInfo, cmdList);

        // 报文发送
        requestPendingCenter.sendCmdList(requestInfo, cmdList);

//        for(ScpCmd cmd:cmdList) {
//            System.out.println(cmd.getCommand());
//        }

//        requestPendingCenter.sendCmdList(requestInfo, cmdList);
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
        cmdList.add(new ScpCmd(scpId, specificationMsg, IdUtil.nextId()));

        // Access Database Specification (Command 1105)
        DevControllerCommonAttribute detail = devControllerCommonAttributeService.getADSpecification();
        AccessDatabaseSpecification adSpecification = AccessDatabaseSpecification.fromDb(scpId, detail);
        String adSpecificationMsg = RequestMessage.encode(scpId, adSpecification);
        cmdList.add(new ScpCmd(scpId, adSpecificationMsg, IdUtil.nextId()));

        // 判断是否是梯控设备
        boolean isEleScp = controllerDetailService.isEleScp(scpId);
        if (!isEleScp) {
            return;
        }
        // Command 501: Elevator Access Level Specification
        int floors = Optional.ofNullable(detail.getElevatorFloorNum()).orElse(64);
        ElevatorALsSpecification specification = new ElevatorALsSpecification(scpId, floors);
        String msg = RequestMessage.encode(scpId, specification);
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

    /**
     * 事件触发器配置
     * @param scpId
     * @param cmdList
     */
    public void triggerConfig(int scpId, List<ScpCmd> cmdList) {
        // 313 触发器变量
        List<TriggerVar> triggerVarList = triggerVarService.getTriVarByScpId(scpId);
        for (TriggerVar var:triggerVarList) {
            TriggerVariableControl option = TriggerVariableControl.fromDb(var);
            String msg = RequestMessage.encode(scpId, option);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }

        // 118
        List<TrigScpProcDetail> details = trigScpProcDetailService.getProcDetailsByScp(scpId);
        for (TrigScpProcDetail detail: details) {
            ActionSpecification specification = ActionSpecification.fromDb(detail);
            String msg = RequestMessage.encode(scpId, specification);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }

        // 1117  TriggerSpecificationExtend
        List<TriggerInfoEx> triggerInfoExList = triggerInfoService.getTriggerInfoForScp(scpId);
//        log.info("[before] scpId:{}  1117 size: {}, cmdList size:{}", scpId, triggerInfoExList.size(), cmdList.size());
        for(TriggerInfoEx trigger:triggerInfoExList) {
            TriggerSpecificationExtend specification = TriggerSpecificationExtend.fromDb(trigger);
            String msg = RequestMessage.encode(scpId, specification);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
//            log.info("adding {}", cmdList.size());
        }
//        log.info("[after] scpId:{} cmdList size:{}", scpId, cmdList.size());
    }
}
