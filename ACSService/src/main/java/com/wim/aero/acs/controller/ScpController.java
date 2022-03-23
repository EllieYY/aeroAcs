package com.wim.aero.acs.controller;

import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: WebController
 * @author: Ellie
 * @date: 2022/03/21 09:49
 * @description:
 **/
@RestController
@Slf4j
@RequestMapping("/device/scp")
public class ScpController {
    @RequestMapping(value = "/his/fan/dayCapacity", method = {RequestMethod.POST})
    public ResultBean<String> getHisPowerByFanDayCapacity() {

//        profitCalcService.saveFanDayCapacity();
        return ResultBeanUtil.makeOkResp("使用风机日发电量生成历史利润数据。");
    }

}
