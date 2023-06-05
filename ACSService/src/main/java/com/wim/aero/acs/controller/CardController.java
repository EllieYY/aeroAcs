package com.wim.aero.acs.controller;


import com.wim.aero.acs.model.request.CardBlockedRequestInfo;
import com.wim.aero.acs.model.request.CardListDeleteRequest;
import com.wim.aero.acs.model.request.CardRequestInfo;
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

    @ApiOperation(value = "添加卡片到控制器-权限修改")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public ResultBean<String> addCards(@RequestBody CardRequestInfo cardInfo) {
        if (cardInfo.getCardList().size() == 0) {
            return ResultBeanUtil.makeResp(RespCode.INVALID_PARAM, null);
        }

        // 添加卡
        accessConfigService.addCards(cardInfo);

        return ResultBeanUtil.makeOkResp();
    }

    @ApiOperation(value = "删除卡片")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ResultBean<String> deleteCards(@RequestBody CardListDeleteRequest cardInfo) {

        log.info("卡片删除：{}", cardInfo.toString());
        // 删除卡
        accessConfigService.deleteCards(cardInfo);

        return ResultBeanUtil.makeOkResp();
    }

    @ApiOperation(value = "弃用-按人找门-冻结-解冻-挂失-解挂")
    @RequestMapping(value = "/block", method = {RequestMethod.POST})
    public ResultBean<String> blockCards(@RequestBody CardBlockedRequestInfo cardInfo) {
        log.info("冻结or挂失：{}", cardInfo.toString());

        // 卡冻结解冻
        accessConfigService.cardBlocked(cardInfo);

        return ResultBeanUtil.makeOkResp();
    }
}
