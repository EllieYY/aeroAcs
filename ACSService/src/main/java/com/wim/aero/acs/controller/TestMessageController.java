package com.wim.aero.acs.controller;

import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.request.ScpRequestInfo;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.service.SioService;
import com.wim.aero.acs.service.ScpService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: TestMessageController
 * @author: Ellie
 * @date: 2022/04/24 11:11
 * @description:
 **/
@RestController
public class TestMessageController {
    private final SioService sioService;
    private final ScpService scpService;

    @Autowired
    public TestMessageController(SioService sioService, ScpService scpService) {
        this.sioService = sioService;
        this.scpService = scpService;
    }


    @ApiOperation(value = "sio报文")
    @RequestMapping(value = "/protocol/sio", method = {RequestMethod.POST})
    public ResultBean<List<ScpCmd>> sioCmds(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId + "数据不存在。");
        }

        List<ScpCmd> cmdList = new ArrayList<>();
        sioService.sioConfig(scpId, cmdList);
        cmdList.forEach(it -> {
            System.out.println(it.getCommand());
        });

        return ResultBeanUtil.makeOkResp(cmdList);
    }

    @RequestMapping(value = "/protocol/input", method = {RequestMethod.POST})
    public ResultBean<List<ScpCmd>> inputCmds(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId + "数据不存在。");
        }

        List<ScpCmd> cmdList = new ArrayList<>();
        sioService.inputConfig(scpId, cmdList);
        cmdList.forEach(it -> {
            System.out.println(it.getCommand());
        });

        return ResultBeanUtil.makeOkResp(cmdList);
    }

    @RequestMapping(value = "/protocol/output", method = {RequestMethod.POST})
    public ResultBean<List<ScpCmd>> outputCmds(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId + "数据不存在。");
        }

        List<ScpCmd> cmdList = new ArrayList<>();
        sioService.outputConfig(scpId, cmdList);
        cmdList.forEach(it -> {
            System.out.println(it.getCommand());
        });

        return ResultBeanUtil.makeOkResp(cmdList);
    }

    @RequestMapping(value = "/protocol/reader", method = {RequestMethod.POST})
    public ResultBean<List<ScpCmd>> readerCmds(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId + "数据不存在。");
        }

        List<ScpCmd> cmdList = new ArrayList<>();
        sioService.readerConfig(scpId, cmdList);
        cmdList.forEach(it -> {
            System.out.println(it.getCommand());
        });

        return ResultBeanUtil.makeOkResp(cmdList);
    }


}
