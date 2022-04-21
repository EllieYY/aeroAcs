package com.wim.aero.acs.controller;

import com.wim.aero.acs.model.command.CmdDownloadInfo;
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
    public ResultBean<List<CmdDownloadInfo>> addCards(@RequestBody CardListInfo cardInfo) throws Exception {
        if (cardInfo.getCardList().size() == 0) {
            return ResultBeanUtil.makeResp(RespCode.INVALID_PARAM, null);
        }

        // 添加卡
        accessConfigService.addCards(
                cardInfo.getTaskId(),
                cardInfo.getTaskName(),
                cardInfo.getTaskSource(),
                cardInfo.getCardList());

        return ResultBeanUtil.makeOkResp();
    }

    @ApiOperation(value = "删除卡片")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ResultBean<List<CmdDownloadInfo>> deleteCards(@RequestBody CardListInfo cardNoList) throws Exception {
        if (cardNoList.getCardList().size() == 0) {
            return ResultBeanUtil.makeResp(RespCode.INVALID_PARAM, null);
        }


        return ResultBeanUtil.makeOkResp();
    }
}
