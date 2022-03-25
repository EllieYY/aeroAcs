package com.wim.aero.acs.controller;

import com.google.gson.JsonObject;
import com.wim.aero.acs.db.entity.DevControllerDetail;
import com.wim.aero.acs.db.mapper.DevControllerDetailMapper;
import com.wim.aero.acs.message.Message;
import com.wim.aero.acs.message.RequestMessage;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.protocol.device.SCPDriver;
import com.wim.aero.acs.service.AccessConfigService;
import com.wim.aero.acs.service.ScpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    ScpService scpService;
    @Autowired
    AccessConfigService accessConfigService;

    @RequestMapping(value = "/scpInfo", method = {RequestMethod.POST})
    public ResultBean<String> scpConfig(@RequestParam(value = "scpId", required = true) String scpIdStr) {

        int scpId = Integer.parseInt(scpIdStr);
//        scpService.configScp(scpId);
        accessConfigService.mpGroupConfig(scpId);

        return ResultBeanUtil.makeOkResp("count = ");
    }
}
