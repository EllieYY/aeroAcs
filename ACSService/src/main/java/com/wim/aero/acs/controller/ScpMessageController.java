package com.wim.aero.acs.controller;

import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.request.CardCmd;
import com.wim.aero.acs.model.request.CardCmdReloadRequest;
import com.wim.aero.acs.model.request.CmdReloadRequestInfo;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.model.scp.reply.SCPReply;
import com.wim.aero.acs.model.scp.reply.SCPReplyTranStatus;
import com.wim.aero.acs.model.scp.transaction.SCPReplyTransaction;
import com.wim.aero.acs.service.RequestPendingCenter;
import com.wim.aero.acs.service.ScpMessageService;
import com.wim.aero.acs.util.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: ScpMessageController
 * @author: Ellie
 * @date: 2022/04/01 11:31
 * @description:
 **/
@RestController
@Slf4j
@RequestMapping("/message/scp")
@Api(tags = "控制器消息接口")
public class ScpMessageController {

    private final ScpMessageService scpMessageService;
    private final RequestPendingCenter requestPendingCenter;
    @Autowired
    public ScpMessageController(ScpMessageService scpMessageService,
                                RequestPendingCenter requestPendingCenter) {
        this.scpMessageService = scpMessageService;
        this.requestPendingCenter = requestPendingCenter;
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "失败任务指令重发")
    @RequestMapping(value = "/cmd/redo", method = {RequestMethod.POST})
    public ResultBean<String> scpCmdRedo(@RequestBody CmdReloadRequestInfo request) {
        log.info(request.toString());

        requestPendingCenter.sendCmdList(request, request.getCmdList());

        return ResultBeanUtil.makeOkResp(request.toString());
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "失败授权指令重发")
    @RequestMapping(value = "/cmd/card/redo", method = {RequestMethod.POST})
    public ResultBean<String> cardCmdRedo(@RequestBody CardCmdReloadRequest request) {
        log.info(request.toString());

        // 命令重发
        List<CardCmd> cardCmdList = request.getCmdList();
        List<ScpCmd> cmdList = new ArrayList<>();
        for (CardCmd cardCmd:cardCmdList) {
            ScpCmd cmd = new ScpCmd(cardCmd.getScpId(), cardCmd.getCommand(), IdUtil.nextId());
            cmd.setType(cardCmd.getType());
            cmd.setAlvlListStr(cardCmd.getAlvlListStr());
            cmd.setCardNo(cardCmd.getCardNo());
            cmdList.add(cmd);
        }
        requestPendingCenter.sendCmdList(request, cmdList);

        return ResultBeanUtil.makeOkResp(request.toString());
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "ScpReply消息上报")
    @RequestMapping(value = "/reply", method = {RequestMethod.POST})
    public ResultBean<String> scpReplyNotify(@RequestBody SCPReply request) {
        if (request.getScpId() <= 0) {
            return ResultBeanUtil.makeParamInvalidResp(request.toString());
        }
//        log.info(request.toString());

        // 数据处理
        scpMessageService.dealScpReply(request);

        return ResultBeanUtil.makeOkResp(request.toString());
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "transaction消息通知")
    @RequestMapping(value = "/transaction", method = {RequestMethod.POST})
    public ResultBean<String> scpTransactionNotify(@RequestBody SCPReplyTransaction request) {
        if (request.getScpId() <= 0) {
            return ResultBeanUtil.makeParamInvalidResp(request.toString());
        }

//        log.info(request.toString());

        scpMessageService.dealTransaction(request);
        return ResultBeanUtil.makeOkResp(request.toString());
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @Deprecated
    @ApiOperation(value = "transaction状态通知")
    @RequestMapping(value = "/transStatus", method = {RequestMethod.POST})
    public ResultBean<String> scpTransStatusNotify(@RequestBody SCPReplyTranStatus request) {
        if (request.getScpId() <= 0) {
            return ResultBeanUtil.makeParamInvalidResp(request.toString());
        }

//        log.info(request.toString());

        return ResultBeanUtil.makeOkResp(request.toString());
    }
}
