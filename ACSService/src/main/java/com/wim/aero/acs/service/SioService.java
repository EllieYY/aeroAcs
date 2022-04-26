package com.wim.aero.acs.service;

import com.wim.aero.acs.db.entity.DevInputDetail;
import com.wim.aero.acs.db.entity.DevOutputDetail;
import com.wim.aero.acs.db.entity.DevReaderDetail;
import com.wim.aero.acs.db.entity.DevXDetail;
import com.wim.aero.acs.db.service.impl.DevInputDetailServiceImpl;
import com.wim.aero.acs.db.service.impl.DevOutputDetailServiceImpl;
import com.wim.aero.acs.db.service.impl.DevReaderDetailServiceImpl;
import com.wim.aero.acs.db.service.impl.DevXDetailServiceImpl;
import com.wim.aero.acs.message.RequestMessage;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.command.ScpCmdResponse;
import com.wim.aero.acs.model.request.ScpRequestInfo;
import com.wim.aero.acs.protocol.device.*;
import com.wim.aero.acs.protocol.device.cp.ControlPointCommand;
import com.wim.aero.acs.protocol.device.cp.ControlPointCommandType;
import com.wim.aero.acs.protocol.device.cp.ControlPointConfig;
import com.wim.aero.acs.protocol.device.cp.OutputPointSpecification;
import com.wim.aero.acs.protocol.device.mp.InputPointSpecification;
import com.wim.aero.acs.protocol.device.mp.MonitorPointConfig;
import com.wim.aero.acs.protocol.device.mp.MonitorPointMask;
import com.wim.aero.acs.protocol.device.mp.MpGroupCommand;
import com.wim.aero.acs.protocol.device.reader.ACRConfig;
import com.wim.aero.acs.protocol.device.reader.ACRModeConfig;
import com.wim.aero.acs.protocol.device.reader.ReaderSpecification;
import com.wim.aero.acs.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: SIOService
 * @author: Ellie
 * @date: 2022/03/23 15:38
 * @description: SIO板配置及读卡器、报警点、控制点远程控制命令下发
 **/
@Service
@Slf4j
public class SioService {
    private final DevXDetailServiceImpl sioDetailService;
    private final DevInputDetailServiceImpl inputDetailService;
    private final DevOutputDetailServiceImpl outputDetailService;
    private final DevReaderDetailServiceImpl readerDetailService;
    private final RestUtil restUtil;
    private final RequestPendingCenter requestPendingCenter;
    @Autowired
    public SioService(DevXDetailServiceImpl sioDetailService,
                      DevInputDetailServiceImpl inputDetailService,
                      DevOutputDetailServiceImpl outputDetailService,
                      DevReaderDetailServiceImpl readerDetailService,
                      RestUtil restUtil,
                      RequestPendingCenter requestPendingCenter) {
        this.sioDetailService = sioDetailService;
        this.inputDetailService = inputDetailService;
        this.outputDetailService = outputDetailService;
        this.readerDetailService = readerDetailService;
        this.restUtil = restUtil;
        this.requestPendingCenter = requestPendingCenter;
    }

    /**
     * 硬件配置
     * @param
     */
    public void configSioForScp(ScpRequestInfo requestInfo) {
        int scpId = requestInfo.getScpId();
        List<ScpCmd> cmdList = new ArrayList<>();

        sioConfig(scpId, cmdList);
        inputConfig(scpId, cmdList);
        outputConfig(scpId, cmdList);
        readerConfig(scpId, cmdList);

        for(ScpCmd cmd:cmdList) {
            log.info(cmd.getCommand());
        }

        // TODO:优化
        requestPendingCenter.add(requestInfo.getTaskId(), requestInfo.getTaskName(), requestInfo.getTaskSource(), cmdList);
        List<ScpCmdResponse> responseList = restUtil.sendMultiCmd(cmdList);
        requestPendingCenter.updateSeq(scpId, responseList);
    }


    /**
     * 读卡器mode设置
     * @param scpId
     * @param acrId
     * @param mode
     */
    public int setAcrMode(int scpId, int acrId, int mode) {
        ACRModeConfig config = new ACRModeConfig(scpId, acrId, mode);
        String msg = RequestMessage.encode(scpId, config);

        log.info("[ACR Mode]常开常闭 scpId={}, msg={}", scpId, msg);

        // 向设备发送
        ScpCmdResponse response = restUtil.sendSingleCmd(new ScpCmd(scpId, msg, IdUtil.nextId()));
        log.info("[ACR Mode]常开常闭 response={}", response);

        return response.getCode();
    }

    /**
     * 控制点远程控制命令
     * @param scpId
     * @param cpId
     * @param type
     */
    public int sendControlPointCommand(int scpId, int cpId, ControlPointCommandType type) {
        ControlPointCommand command = new ControlPointCommand(scpId, cpId, type.getCode());
        String msg = RequestMessage.encode(scpId, command);

        log.info("[CP]控制点命令发送 scpId={}, msg={}", scpId, msg);

        // 向设备发送
        ScpCmdResponse response = restUtil.sendSingleCmd(new ScpCmd(scpId, msg, IdUtil.nextId()));
        log.info("[CP]控制点命令发送 response={}", response);

        return response.getCode();
    }

    /**
     * 报警点设防和撤防
     * @param isMask true撤防 false设防
     */
    public int maskMp(int scpId, int mpId, boolean isMask) {
        MonitorPointMask mask = new MonitorPointMask(scpId, mpId, isMask);
        String msg = RequestMessage.encode(scpId, mask);

        log.info("[MP - 设防/撤防] scpId={}, msg={}", scpId, msg);

        // 向设备发送
        ScpCmdResponse response = restUtil.sendSingleCmd(new ScpCmd(scpId, msg, IdUtil.nextId()));
        log.info("[MP - 设防/撤防] response={}", response);

        return response.getCode();
    }

    /**
     * 防区一键撤防和设防
     * @param scpId
     * @param mpId
     * @param isMask true撤防 false设防
     * @return
     */
    public int maskMpg(int scpId, int mpId, boolean isMask) {
        MpGroupCommand mask = MpGroupCommand.setMask(scpId, mpId, isMask);
        String msg = RequestMessage.encode(scpId, mask);

        log.info("[MPGroup - 设防/撤防] scpId={}, msg={}", scpId, msg);

        // 向设备发送
        ScpCmdResponse response = restUtil.sendSingleCmd(new ScpCmd(scpId, msg, IdUtil.nextId()));
        log.info("[MPGroup - 设防/撤防] response={}", response);

        return response.getCode();
    }


    /**
     * sio配置
     * @param scpId
     * @param cmdList
     */
    public void sioConfig(int scpId, List<ScpCmd> cmdList) {
        // MSP1(SIO)Comm. Driver Configuration (Command 108) -- // 一个控制器3个
//        cmdList.add(packageDriver(scpId, 0));
        cmdList.add(packageDriver(scpId, 1));
        cmdList.add(packageDriver(scpId, 2));

        // 查找所有sio
        List<DevXDetail> sioList = sioDetailService.getByScpId(scpId);
        for (DevXDetail sio:sioList) {
            // SIOPanel Configuration (Command 109)
            SIOSpecification specification = SIOSpecification.fromDb(sio);
            String msg = RequestMessage.encode(scpId, specification);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }
    }

    private ScpCmd packageDriver(int scpId, int port) {
        // MSP1(SIO)Comm. Driver Configuration (Command 108) -- // 一个控制器3个
        SIODriver driver = new SIODriver(scpId, port, port);
        String driverMsg = RequestMessage.encode(scpId, driver);
        return new ScpCmd(scpId, driverMsg, IdUtil.nextId());
    }

    /**
     * 输入点（报警点）配置
     * @param scpId
     * @param cmdList
     */
    public void inputConfig(int scpId, List<ScpCmd> cmdList) {
        List<DevInputDetail> devInputDetails = inputDetailService.getByScpId(scpId);
        for (DevInputDetail input:devInputDetails) {
            // Input Point Configuration (Command 110)
            InputPointSpecification specification = InputPointSpecification.fromDb(input);
            String specificationMsg = RequestMessage.encode(scpId, specification);
            cmdList.add(new ScpCmd(scpId, specificationMsg, IdUtil.nextId()));

            // Monitor Point Configuration(Command113)
            MonitorPointConfig config = MonitorPointConfig.fromDb(input);
            String configMsg = RequestMessage.encode(scpId, config);
            cmdList.add(new ScpCmd(scpId, configMsg, IdUtil.nextId()));
        }
    }


    /**
     * 输出点（控制点）配置
     * @param scpId
     * @param cmdList
     */
    public void outputConfig(int scpId, List<ScpCmd> cmdList) {
        List<DevOutputDetail> devOutputDetails = outputDetailService.getByScpId(scpId);
        for (DevOutputDetail output:devOutputDetails) {
            // OutputPointConfiguration (Command 111)
            OutputPointSpecification specification = OutputPointSpecification.fromDb(output);
            String specificationMsg = RequestMessage.encode(scpId, specification);
            cmdList.add(new ScpCmd(scpId, specificationMsg, IdUtil.nextId()));

            // ControlPointConfiguration (Command 114)
            ControlPointConfig config = ControlPointConfig.fromDb(output);
            String configMsg = RequestMessage.encode(scpId, config);
            cmdList.add(new ScpCmd(scpId, configMsg, IdUtil.nextId()));

        }
    }

    /**
     * 读卡器（ACR）配置
     * @param scpId
     * @param cmdList
     */
    public void readerConfig(int scpId, List<ScpCmd> cmdList) {
        List<DevReaderDetail> readerDetails = readerDetailService.getByScpId(scpId);
        for (DevReaderDetail reader:readerDetails) {
            // Card Reader Configuration(Command112)
            ReaderSpecification specification = ReaderSpecification.fromDb(reader);
            String specificationMsg = RequestMessage.encode(scpId, specification);
            cmdList.add(new ScpCmd(scpId, specificationMsg, IdUtil.nextId()));

            // Access Control Reader Configuration(Command115)
            ACRConfig config = ACRConfig.fromDb(reader);
            String configMsg = RequestMessage.encode(scpId, config);
            cmdList.add(new ScpCmd(scpId, configMsg, IdUtil.nextId()));
        }
    }


}