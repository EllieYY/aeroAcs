package com.wim.aero.acs.controller;

import com.wim.aero.acs.model.request.AcrRequestInfo;
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
 * @title: ACRController
 * @author: Ellie
 * @date: 2022/03/21 09:56
 * @description:
 **/
@RestController
@Slf4j
@RequestMapping("/device/acr")
@Api(tags = "读卡器远程控制接口")
public class ACRController {
    private final SIOService sioService;
    @Autowired
    public ACRController(SIOService sioService) {
        this.sioService = sioService;
    }


    @ApiOperation(value = "开门、关门")
    @RequestMapping(value = "/strike/command", method = {RequestMethod.POST})
    public ResultBean<String> doorOpen(@RequestBody AcrRequestInfo request) {
        // TODO:命令类型校验
        sioService.sendControlPointCommand(request.getScpId(), request.getStrikeNo(), request.getCommand());

        return ResultBeanUtil.makeOkResp("开门命令已下发");
    }

    @ApiOperation(value = "常开、常闭")
    @RequestMapping(value = "/mode", method = {RequestMethod.POST})
    public ResultBean<String> doorClose(@RequestBody AcrRequestInfo request) {
        // TODO:命令类型校验
        sioService.setAcrMode(request.getScpId(), request.getAcrId(), request.getCommand());

        return ResultBeanUtil.makeOkResp("关门命令已下发");
    }


}
