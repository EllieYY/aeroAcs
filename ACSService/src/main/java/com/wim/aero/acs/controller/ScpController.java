package com.wim.aero.acs.controller;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.command.CmdDownloadInfo;
import com.wim.aero.acs.model.request.*;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.command.ScpCmdResponse;
import com.wim.aero.acs.model.result.RespCode;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.protocol.device.cp.ControlPointCommandType;
import com.wim.aero.acs.protocol.trigger.PrefixType;
import com.wim.aero.acs.service.*;
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
 * @title: WebController
 * @author: Ellie
 * @date: 2022/03/21 09:49
 * @description:
 **/
@RestController
@Slf4j
@RequestMapping("/device/scp")
@Api(tags = "控制器配置及远程控制接口")
public class ScpController {

    private final ScpService scpService;
    private final SioService sioService;
    private final AccessConfigService accessConfigService;
    private final RestUtil restUtil;
    private final RequestPendingCenter requestPendingCenter;
    private final ScpCenter scpCenter;
    @Autowired
    public ScpController(ScpService scpService,
                         SioService sioService,
                         AccessConfigService accessConfigService,
                         RestUtil restUtil, RequestPendingCenter requestPendingCenter, ScpCenter scpCenter) {
        this.scpService = scpService;
        this.sioService = sioService;
        this.accessConfigService = accessConfigService;
        this.restUtil = restUtil;
        this.requestPendingCenter = requestPendingCenter;
        this.scpCenter = scpCenter;
    }

    @ApiOperation(value = "硬件连接")
    @RequestMapping(value = "/connect", method = {RequestMethod.POST})
    public ResultBean<String> connectScp(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId +"数据不存在。");
        }

        // 命令组装+记录+scp影子对象维护
        scpService.connectScp(request);

        return ResultBeanUtil.makeOkResp("正在与scp建立连接...");
    }

    @ApiOperation(value = "硬件配置")
    @RequestMapping(value = "/config", method = {RequestMethod.POST})
    public ResultBean<String> configScpDeivce(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId +"数据不存在。");
        }

        List<ScpCmd> cmdList = new ArrayList<>();
        scpService.configScp(request, cmdList);
        accessConfigService.accessConfig(request, cmdList);
        sioService.configSioForScp(request, cmdList);

        // 报文发送
        requestPendingCenter.sendCmdList(request, cmdList);

        return ResultBeanUtil.makeOkResp();
    }

    @ApiOperation(value = "硬件删除")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ResultBean<String> deleteScp(@RequestBody ScpRequestInfo request) {
        scpService.deleteScp(request);

        return ResultBeanUtil.makeOkResp("正在与scp断开连接...");
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @Deprecated
    @ApiOperation(value = "配置命令执行状态列表通知接口")
    @RequestMapping(value = "/cmd/notify/list", method = {RequestMethod.POST})
    public ResultBean<String> scpCmdNotifyList(@RequestBody List<ScpCmdResponse> request) {
//        log.info("执行结果。{}", request.toString());
        // TODO:结果匹配

        return ResultBeanUtil.makeOkResp(request.toString());
    }

    /**
     * @param request
     * @return
     * @throws Exception
     */
    @Deprecated
    @ApiOperation(value = "单条配置命令执行状态通知接口")
    @RequestMapping(value = "/cmd/notify", method = {RequestMethod.POST})
    public ResultBean<String> scpCmdNotify(@RequestBody ScpCmdResponse request) {
//        log.info("执行结果。{}", request.toString());
        // TODO:结果匹配

        return ResultBeanUtil.makeOkResp(request.toString());
    }

    @ApiOperation(value = "控制器复位")
    @RequestMapping(value = "/reset", method = {RequestMethod.POST})
    public ResultBean<String> resetScp(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId +"数据不存在。");
        }

        int code = scpService.reset(request);
        if (code == Constants.REST_CODE_SUCCESS) {
            return ResultBeanUtil.makeOkResp("控制器复位命令已下发");
        } else {
            return ResultBeanUtil.makeResp(RespCode.CMD_DOWNLOAD_FAIL, "错误码：" + code);
        }
    }

    @ApiOperation(value = "清除卡片")
    @RequestMapping(value = "/card/clear", method = {RequestMethod.POST})
    public ResultBean<String> clearCards(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId +"数据不存在。");
        }

        int code = scpService.clearCards(request);
        if (code == Constants.REST_CODE_SUCCESS) {
            return ResultBeanUtil.makeOkResp("清除卡片命令已下发");
        } else {
            return ResultBeanUtil.makeResp(RespCode.CMD_DOWNLOAD_FAIL, "错误码：" + code);
        }
    }

    @ApiOperation(value = "下载卡片")
    @RequestMapping(value = "/card/reload", method = {RequestMethod.POST})
    public ResultBean<List<CmdDownloadInfo>> reloadCards(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId +"数据不存在。");
        }

        accessConfigService.downloadCards(request, request.getScpId());

        return ResultBeanUtil.makeOkResp();
    }

    @ApiOperation(value = "下载访问级别")
    @RequestMapping(value = "/alvl/reload", method = {RequestMethod.POST})
    public ResultBean<List<CmdDownloadInfo>> reloadAvls(@RequestBody AlvlRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId +"数据不存在。");
        }

        accessConfigService.accessLevelConfig(request);

        return ResultBeanUtil.makeOkResp();
    }


    @ApiOperation(value = "执行过程")
    @RequestMapping(value = "/procedure/do", method = {RequestMethod.POST})
    public ResultBean<String> doProcedure(@RequestBody ProcedureCommandRequest request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId +"数据不存在。");
        }

        // 前缀校验
        int prefix = request.getPrefix();
        if (!PrefixType.isValidPrefix(prefix)) {
            return ResultBeanUtil.makeResp(RespCode.INVALID_PARAM, request.toString());
        }

        int code = scpService.procedureExecute(request);
        if (code == Constants.REST_CODE_SUCCESS) {
            return ResultBeanUtil.makeOkResp("执行过程命令已下发");
        } else {
            return ResultBeanUtil.makeResp(RespCode.CMD_DOWNLOAD_FAIL, "错误码：" + code);
        }
    }

    @ApiOperation(value = "提取事件")
    @RequestMapping(value = "/events/extract", method = {RequestMethod.POST})
    public ResultBean<String> extractEvents(@RequestBody TransactionRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId +"数据不存在。");
        }

        scpCenter.updateSeqNoInfo(scpId, request.getEventStartNo(), request.getEventEndNo(), request.getEventCurNo());
        int code = scpService.eventExtraction(request);
        if (code == Constants.REST_CODE_SUCCESS) {
            return ResultBeanUtil.makeOkResp("提取事件命令已下发");
        } else {
            return ResultBeanUtil.makeResp(RespCode.CMD_DOWNLOAD_FAIL, "错误码：" + code);
        }
    }

    /**
     * 通信服务与scp成功建立连接之后获取scp对应的配置信息
     * @param
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "通信后台使用 - 获取所有控制器连接报文")
    @RequestMapping(value = "/all/connect", method = {RequestMethod.POST, RequestMethod.GET})
    public List<ScpCmd> scpConfig() {
        return scpService.getAllScpConnectMsg();
    }

    /**
     * 状态通知
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "通信后台使用 - 控制器状态通知")
    @RequestMapping(value = "/update/state", method = {RequestMethod.POST})
    public ResultBean<String> scpStateUpdate(@RequestBody ScpStateNotify request) {
        int scpId = request.getScpId();
//        if (!scpService.isValidScpId(scpId)) {
//            log.error("控制器{}数据不存在。", scpId);
//            return new ArrayList<>();
//        }
//        log.info(request.toString());
        scpService.scpStateUpdate(scpId, request.getState());

        return ResultBeanUtil.makeOkResp();
    }

}
