package com.wim.aero.acs.controller;

import com.wim.aero.acs.model.request.MpRequestInfo;
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
 * @title: MonitorController
 * @author: Ellie
 * @date: 2022/03/21 09:55
 * @description:
 **/
@RestController
@Slf4j
@RequestMapping("/device/mp")
@Api(tags = "报警点远程控制接口")
public class MonitorPointController {

    private final SIOService sioService;
    @Autowired
    public MonitorPointController(SIOService sioService) {
        this.sioService = sioService;
    }

    @ApiOperation(value = "单点设防")
    @RequestMapping(value = "/alarm/point", method = {RequestMethod.POST})
    public ResultBean<String> pointMask(@RequestBody MpRequestInfo request) {
        log.info("[MpMask] {}", request);
        sioService.maskMp(request.getScpId(), request.getMpId(), !request.isSetAlarm());

        return ResultBeanUtil.makeOkResp("单点设防、撤防命令已下发");
    }


    //TODO：防区设防和撤防

}
