package com.wim.aero.acs.controller;

import com.wim.aero.acs.db.service.impl.CCardInfoServiceImpl;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.mq.ScpSeqMessage;
import com.wim.aero.acs.model.request.CardRequestInfo;
import com.wim.aero.acs.model.request.ScpRequestInfo;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.protocol.card.CardAdd;
import com.wim.aero.acs.service.AccessConfigService;
import com.wim.aero.acs.service.QueueProducer;
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
    private final AccessConfigService accessConfigService;
    private final CCardInfoServiceImpl cardInfoService;
    private final QueueProducer queueProducer;

    @Autowired
    public TestMessageController(SioService sioService,
                                 ScpService scpService,
                                 AccessConfigService accessConfigService,
                                 CCardInfoServiceImpl cardInfoService, QueueProducer queueProducer) {
        this.sioService = sioService;
        this.scpService = scpService;
        this.accessConfigService = accessConfigService;
        this.cardInfoService = cardInfoService;
        this.queueProducer = queueProducer;
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
            System.out.println(it.isDelay() + ":" + it.getCommand());
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
            System.out.println(it.isDelay() + ":" + it.getCommand());
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
            System.out.println(it.isDelay() + ":" + it.getCommand());
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
            System.out.println(it.isDelay() + ":" + it.getCommand());
        });

        return ResultBeanUtil.makeOkResp(cmdList);
    }

    @RequestMapping(value = "/protocol/trigger", method = {RequestMethod.POST})
    public ResultBean<List<ScpCmd>> triggerCmds(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId + "数据不存在。");
        }

        List<ScpCmd> cmdList = new ArrayList<>();
        scpService.triggerConfig(scpId, cmdList);
        cmdList.forEach(it -> {
            System.out.println(it.getCommand());
        });

        return ResultBeanUtil.makeOkResp(cmdList);
    }

    @RequestMapping(value = "/protocol/albasic", method = {RequestMethod.POST})
    public ResultBean<List<ScpCmd>> alBasicCmds(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
//        if (!scpService.isValidScpId(scpId)) {
//            return ResultBeanUtil.makeResp(1001, "控制器" + scpId + "数据不存在。");
//        }

        List<ScpCmd> cmdList = new ArrayList<>();
        accessConfigService.alBasicConfigMsg(scpId, cmdList);
        cmdList.forEach(it -> {
            System.out.println(it.getCommand());
        });

        return ResultBeanUtil.makeOkResp(cmdList);
    }

    @RequestMapping(value = "/protocol/addCards", method = {RequestMethod.POST})
    public ResultBean<List<ScpCmd>> addCardCmds(@RequestBody CardRequestInfo request) {

        List<CardAdd> cardAddList = cardInfoService.getByCardList(request.getCardList());
        accessConfigService.packageCardMessages(request, cardAddList);


        return ResultBeanUtil.makeOkResp();
    }

    @RequestMapping(value = "/protocol/downloadCards", method = {RequestMethod.POST})
    public ResultBean<List<ScpCmd>> downloadCardCmds(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId + "数据不存在。");
        }

        List<CardAdd> cardAddList = cardInfoService.getByScpId(scpId);
//        List<ScpCmd> cmdList = accessConfigService.packageCardMessages(request, cardAddList);
//        cmdList.forEach(it -> {
//            System.out.println(it.getCommand());
//        });

        return ResultBeanUtil.makeOkResp();
    }

    @RequestMapping(value = "/test/mq/productor", method = {RequestMethod.POST})
    public ResultBean<String> productMessage(@RequestBody ScpSeqMessage request) {
        //queueProducer.sendScpMessage(request);

//        queueProducer.sendDelayScpMessage(request, 5);


        return ResultBeanUtil.makeOkResp();
    }
}
