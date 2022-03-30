package com.wim.aero.acs.controller;

import com.wim.aero.acs.model.command.ScpCmd;
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
    public ResultBean<String> connectScp(@RequestBody List<String> cardNoList) throws Exception {
        if (cardNoList.size() == 0) {
            return ResultBeanUtil.makeResp(RespCode.INVALID_PARAM, "卡片id列表为空。");
        }

        // 添加卡
        List<ScpCmd> cmdList = new ArrayList<>();
        accessConfigService.addCard(cardNoList, cmdList);

        return ResultBeanUtil.makeOkResp("已执行添加命令");
    }
}
