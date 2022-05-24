package com.wim.aero.acs.db.controller;

import com.wim.aero.acs.db.service.impl.TriggerInfoServiceImpl;
import com.wim.aero.acs.model.db.TriggerInfoEx;
import com.wim.aero.acs.model.request.ScpRequestInfo;
import com.wim.aero.acs.model.result.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ellie
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/db/trigScpProcDetail")
@Slf4j
public class TrigScpProcDetailController {
    @Autowired
    private TriggerInfoServiceImpl triggerInfoService;

    @RequestMapping(value = "/sql", method = RequestMethod.POST)
    public void testSql(@RequestBody ScpRequestInfo requestInfo) {
        List<TriggerInfoEx> result = triggerInfoService.getTriggerInfoForScp(requestInfo.getScpId());
        log.info(result.toString());

    }




}
