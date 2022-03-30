package com.wim.aero.acs.controller;

import com.wim.aero.acs.model.request.CpRequestInfo;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.service.SIOService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: ControlPointController
 * @author: Ellie
 * @date: 2022/03/30 10:30
 * @description:
 **/
@RestController
@Slf4j
@RequestMapping("/device/cp")
@Api(tags = "输出点远程控制接口")
public class ControlPointController {

    private final SIOService sioService;
    @Autowired
    public ControlPointController(SIOService sioService) {
        this.sioService = sioService;
    }

    @ApiOperation(value = "远程控制命令下发")
    @RequestMapping(value = "/command", method = {RequestMethod.POST})
    public ResultBean<String> cpCommand(@RequestBody CpRequestInfo request) {
        sioService.sendControlPointCommand(request.getScpId(), request.getCpId(), request.getCommand());

        return ResultBeanUtil.makeOkResp("命令已下发");
    }

}
