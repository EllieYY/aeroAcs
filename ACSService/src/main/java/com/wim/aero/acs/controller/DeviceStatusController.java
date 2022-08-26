package com.wim.aero.acs.controller;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.request.CpRequestInfo;
import com.wim.aero.acs.model.request.DeviceStatusRequest;
import com.wim.aero.acs.model.request.ScpCardsRequest;
import com.wim.aero.acs.model.result.RespCode;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.protocol.device.cp.ControlPointCommandType;
import com.wim.aero.acs.service.ScpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: DeviceStatusController
 * @author: Ellie
 * @date: 2022/06/14 11:03
 * @description:
 **/
@RestController
@Slf4j
@RequestMapping("/device/status")
@Api(tags = "设备状态查询")
public class DeviceStatusController {
    private final ScpService scpService;

    public DeviceStatusController(ScpService scpService) {
        this.scpService = scpService;
    }

    @ApiOperation(value = "硬件状态查询")
    @RequestMapping(value = "/all", method = {RequestMethod.POST})
    public ResultBean<String> allStatusCommand(@RequestBody DeviceStatusRequest request) {
        scpService.requestDeviceStatus(request);
        return ResultBeanUtil.makeOkResp();
    }

    @ApiOperation(value = "卡数量和MAC查询")
    @RequestMapping(value = "/cards", method = {RequestMethod.POST})
    public ResultBean<String> cardsCountCommand(@RequestBody ScpCardsRequest request) {

        scpService.cardAmountRequest(request);
        return ResultBeanUtil.makeOkResp();
    }
}
