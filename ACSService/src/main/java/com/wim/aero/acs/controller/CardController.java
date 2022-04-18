package com.wim.aero.acs.controller;

import com.wim.aero.acs.model.command.CmdDownloadInfo;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.request.CardListInfo;
import com.wim.aero.acs.model.result.RespCode;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.service.AccessConfigService;
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
 * @title: CardController
 * @author: Ellie
 * @date: 2022/03/21 09:56
 * @description:
 **/
@RestController
@Slf4j
@RequestMapping("/device/card")
@Api(tags = "卡片增删改")
public class CardController {
    private final AccessConfigService accessConfigService;
    @Autowired
    public CardController(AccessConfigService accessConfigService) {
        this.accessConfigService = accessConfigService;
    }

    @ApiOperation(value = "添加卡片到控制器")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public ResultBean<List<CmdDownloadInfo>> connectScp(@RequestBody CardListInfo cardNoList) throws Exception {
        if (cardNoList.getCardList().size() == 0) {
            return ResultBeanUtil.makeResp(RespCode.INVALID_PARAM, null);
        }

        // 添加卡
        List<CmdDownloadInfo> result = accessConfigService.addCard(cardNoList.getCardList());
        if (result.size() > 0) {
            log.info("下发失败卡片信息：{}", result);
            ResultBeanUtil.makeResp(RespCode.CMD_DOWNLOAD_FAIL, result);
        }

        return ResultBeanUtil.makeOkResp();
    }
}
