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
import com.wim.aero.acs.model.rest.ScpCmd;
import com.wim.aero.acs.protocol.device.*;
import com.wim.aero.acs.protocol.device.reader.ACRConfig;
import com.wim.aero.acs.protocol.device.reader.ReaderSpecification;
import com.wim.aero.acs.util.IdUtil;
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
public class SIOService {
    private final DevXDetailServiceImpl sioDetailService;
    private final DevInputDetailServiceImpl inputDetailService;
    private final DevOutputDetailServiceImpl outputDetailService;
    private final DevReaderDetailServiceImpl readerDetailService;
    @Autowired
    public SIOService(DevXDetailServiceImpl sioDetailService,
                      DevInputDetailServiceImpl inputDetailService,
                      DevOutputDetailServiceImpl outputDetailService,
                      DevReaderDetailServiceImpl readerDetailService) {
        this.sioDetailService = sioDetailService;
        this.inputDetailService = inputDetailService;
        this.outputDetailService = outputDetailService;
        this.readerDetailService = readerDetailService;
    }

    public void configSioForScp(int scpId, List<ScpCmd> cmdList) {
        // MSP1(SIO)Comm. Driver Configuration (Command 108) -- // 一个控制器两个
        SIODriver driver1 = new SIODriver(scpId, 1, 1);
        SIODriver driver2 = new SIODriver(scpId, 2, 2);

        // 报文组装
        String msg1 = RequestMessage.encode(scpId, driver1);
        String msg2 = RequestMessage.encode(scpId, driver2);
        cmdList.add(new ScpCmd(scpId, msg1, IdUtil.nextId()));
        cmdList.add(new ScpCmd(scpId, msg2, IdUtil.nextId()));

        // 查找所有sio
        List<DevXDetail> sioList = sioDetailService.getByScpId(scpId);
        for (DevXDetail sio:sioList) {
            // SIOPanel Configuration (Command 109)
            SIOSpecification specification = SIOSpecification.fromDb(sio);
            String msg = RequestMessage.encode(scpId, specification);
            cmdList.add(new ScpCmd(scpId, msg, IdUtil.nextId()));
        }

        // 通过上级设备id查找输入输出点，也可以直接通过scpId查找
//        List<Integer> pDeviceIds = sioList.stream().collect(ArrayList::new, (list, item) -> {
//            list.add(item.getDeviceId());
//        }, ArrayList::addAll);
//        pDeviceIds.add(scpId);   // 控制器的输入输出点及读卡器也要配置
        inputConfig(scpId, cmdList);
        outputConfig(scpId, cmdList);
        readerConfig(scpId, cmdList);
    }

    private void inputConfig(int scpId, List<ScpCmd> cmdList) {
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

    private void outputConfig(int scpId, List<ScpCmd> cmdList) {
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

    private void readerConfig(int scpId, List<ScpCmd> cmdList) {
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
