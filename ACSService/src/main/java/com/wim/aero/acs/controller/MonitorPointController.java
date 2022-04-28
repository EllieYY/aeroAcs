package com.wim.aero.acs.controller;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.request.MpRequestInfo;
import com.wim.aero.acs.model.result.RespCode;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.service.SioService;
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

    private final SioService sioService;
    @Autowired
    public MonitorPointController(SioService sioService) {
        this.sioService = sioService;
    }

    @ApiOperation(value = "单点设防")
    @RequestMapping(value = "/alarm/point", method = {RequestMethod.POST})
    public ResultBean<String> pointMask(@RequestBody MpRequestInfo request) {
        log.info("[MpMask] {}", request);
        int code = sioService.maskMp(request, request.getScpId(), request.getId(), !request.isSetAlarm());
        if (code == Constants.REST_CODE_SUCCESS) {
            return ResultBeanUtil.makeOkResp("命令下发成功");
        }

        return ResultBeanUtil.makeResp(RespCode.CMD_DOWNLOAD_FAIL, "错误码：" + code);
    }


    @ApiOperation(value = "防区设防和撤防")
    @RequestMapping(value = "/alarm/pointGroup", method = {RequestMethod.POST})
    public ResultBean<String> mpGroupMask(@RequestBody MpRequestInfo request) {
        log.info("[MpGroupMask] {}", request);
        int code = sioService.maskMpg(request, request.getScpId(), request.getId(), !request.isSetAlarm());
        if (code == Constants.REST_CODE_SUCCESS) {
            return ResultBeanUtil.makeOkResp("命令下发成功");
        }

        return ResultBeanUtil.makeResp(RespCode.CMD_DOWNLOAD_FAIL, "错误码：" + code);
    }

}
