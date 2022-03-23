package com.wim.aero.acs.controller;

import com.google.gson.JsonObject;
import com.wim.aero.acs.db.entity.DevControllerDetail;
import com.wim.aero.acs.db.mapper.DevControllerDetailMapper;
import com.wim.aero.acs.message.Message;
import com.wim.aero.acs.message.RequestMessage;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.protocol.device.SCPDriver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @title: TestController
 * @author: Ellie
 * @date: 2022/03/21 15:11
 * @description:
 **/
@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {
    @Autowired
    DevControllerDetailMapper devControllerDetailMapper;

    @RequestMapping(value = "/scpInfo", method = {RequestMethod.POST})
    public ResultBean<String> scpConfig() {
        String scpIdStr = "20";
//        String scpIdStr = jsonObject.get("scpId").toString();
        int scpId = Integer.parseInt(scpIdStr);

//        long count = devControllerDetailService.count();
        List<DevControllerDetail> detailList = devControllerDetailMapper.selectAllByDeviceId(scpId);
        for (DevControllerDetail detail:detailList) {
            SCPDriver driver = SCPDriver.fromDb(detail);

            // 报文编码
            RequestMessage message = new RequestMessage(scpId, driver);
            String msg = message.encode();
            System.out.println(msg);
        }

        return ResultBeanUtil.makeOkResp("count = ");
    }
}
