package com.wim.aero.acs.controller;

import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.request.CmdReloadRequestInfo;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.model.scp.reply.SCPReply;
import com.wim.aero.acs.model.scp.reply.SCPReplyTranStatus;
import com.wim.aero.acs.model.scp.transaction.SCPReplyTransaction;
import com.wim.aero.acs.service.RequestPendingCenter;
import com.wim.aero.acs.service.ScpMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "控制器消息上报接口")
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
    @ApiOperation(value = "失败指令重发")
    @RequestMapping(value = "/cmd/redo", method = {RequestMethod.POST})
    public ResultBean<String> scpNakNotify(@RequestBody CmdReloadRequestInfo request) {
        log.info(request.toString());

        requestPendingCenter.sendCmdList(request, request.getCmdList());

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
        scpMessageService.dealScpeply(request);

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
        // TODO:结果匹配

        return ResultBeanUtil.makeOkResp(request.toString());
    }
}
