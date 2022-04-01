package com.wim.aero.acs.controller;

import com.wim.aero.acs.model.request.ScpRequestInfo;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.command.ScpCmdResponse;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.service.AccessConfigService;
import com.wim.aero.acs.service.RestUtil;
import com.wim.aero.acs.service.SIOService;
import com.wim.aero.acs.service.ScpService;
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
    private final SIOService sioService;
    private final AccessConfigService accessConfigService;
    private final RestUtil restUtil;
    @Autowired
    public ScpController(ScpService scpService,
                         SIOService sioService,
                         AccessConfigService accessConfigService,
                         RestUtil restUtil) {
        this.scpService = scpService;
        this.sioService = sioService;
        this.accessConfigService = accessConfigService;
        this.restUtil = restUtil;
    }

    @ApiOperation(value = "获取控制器连接信息-1013")
    @RequestMapping(value = "/connect", method = {RequestMethod.POST})
    public ResultBean<String> connectScp(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            return ResultBeanUtil.makeResp(1001, "控制器" + scpId +"数据不存在。");
        }

        ScpCmd cmd = scpService.connectScp(scpId);

        // 通过restUtil发送
        ScpCmdResponse response = restUtil.sendSingleCmd(cmd);

        // TODO:结果校验

        return ResultBeanUtil.makeOkResp("正在与scp建立连接...");
    }

//    @Operation(summary = "管理员更新用户", description = "管理员根据姓名更新用户")
////    @ApiResponse(description = "返回更新的用户", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
//    CommonResult<User> updateUser(@Parameter(schema = @Schema(implementation = User.class), required = true, description = "用户类") User user);

    /**
     * 通信服务与scp成功建立连接之后获取scp对应的配置信息
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取控制器配置报文")
    @RequestMapping(value = "/config", method = {RequestMethod.POST})
    public List<ScpCmd> scpConfig(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();
        if (!scpService.isValidScpId(scpId)) {
            log.error("控制器{}数据不存在。", scpId);
            return new ArrayList<>();
        }
        // TODO:修改scp状态 -- 数据库

        // scp配置
        List<ScpCmd> cmdList = scpService.configScp(scpId);
        // sio及物理点位配置
        sioService.configSioForScp(scpId, cmdList);
        // 时间组、访问组配置
        accessConfigService.alBasicConfig(scpId, cmdList);

        return cmdList;
    }


    /**
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "命令执行状态通知接口")
    @RequestMapping(value = "/cmd/notify", method = {RequestMethod.POST})
    public ResultBean<String> scpCmdNotify(@RequestBody List<ScpCmdResponse> request) {
        log.info(request.toString());
        // TODO:结果匹配

        return ResultBeanUtil.makeOkResp(request.toString());
    }

    @ApiOperation(value = "控制器复位")
    @RequestMapping(value = "/reset", method = {RequestMethod.POST})
    public ResultBean<String> resetScp(@RequestBody ScpRequestInfo request) throws Exception {
        // TODO:

        return ResultBeanUtil.makeOkResp("控制器复位命令已下发");
    }

    @ApiOperation(value = "清除卡片")
    @RequestMapping(value = "/card/clear", method = {RequestMethod.POST})
    public ResultBean<String> clearCards(@RequestBody ScpRequestInfo request) throws Exception {
        // TODO:

        return ResultBeanUtil.makeOkResp("清除卡片命令已下发");
    }

    @ApiOperation(value = "下载卡片")
    @RequestMapping(value = "/card/reload", method = {RequestMethod.POST})
    public ResultBean<String> reloadCards(@RequestBody ScpRequestInfo request) throws Exception {
        // TODO:

        return ResultBeanUtil.makeOkResp("下载卡片命令已下发");
    }

    @ApiOperation(value = "提取事件")
    @RequestMapping(value = "/events/extract", method = {RequestMethod.POST})
    public ResultBean<String> extractEvents(@RequestBody ScpRequestInfo request) throws Exception {
        // TODO:

        return ResultBeanUtil.makeOkResp("提取事件命令已下发");
    }

    @ApiOperation(value = "执行过程")
    @RequestMapping(value = "/process/act", method = {RequestMethod.POST})
    public ResultBean<String> activeProcess(@RequestBody ScpRequestInfo request) throws Exception {
        // TODO:

        return ResultBeanUtil.makeOkResp("执行过程命令已下发");
    }
}
