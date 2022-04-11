package com.wim.aero.acs.controller;

import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.model.scpmessage.SCPReplyTranStatus;
import com.wim.aero.acs.model.scpmessage.SCPReplyTransaction;
import com.wim.aero.acs.model.scpmessage.ScpReplayNAK;
import com.wim.aero.acs.model.scpmessage.TransactionBody;
import com.wim.aero.acs.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: ScpMessageController
 * @author: Ellie
 * @date: 2022/04/01 11:31
 * @description:
 **/
@RestController
@Slf4j
@RequestMapping("/device/scp")
@Api(tags = "控制器消息上报接口")
public class ScpMessageController {

    private final TransactionService transactionService;
    @Autowired
    public ScpMessageController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "NAK消息上报")
    @RequestMapping(value = "/message/nak", method = {RequestMethod.POST})
    public ResultBean<String> scpNakNotify(@RequestBody ScpReplayNAK request) {
        log.info(request.toString());
        // TODO:结果匹配

        return ResultBeanUtil.makeOkResp(request.toString());
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "transaction消息通知")
    @RequestMapping(value = "/message/transaction", method = {RequestMethod.POST})
    public ResultBean<String> scpTransactionNotify(@RequestBody SCPReplyTransaction request) {
        log.info(request.toString());

        transactionService.dealTransaction(request);
        return ResultBeanUtil.makeOkResp(request.toString());
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "transaction状态通知")
    @RequestMapping(value = "/message/transStatus", method = {RequestMethod.POST})
    public ResultBean<String> scpTransStatusNotify(@RequestBody SCPReplyTranStatus request) {
        log.info(request.toString());
        // TODO:结果匹配


        return ResultBeanUtil.makeOkResp(request.toString());
    }
}
