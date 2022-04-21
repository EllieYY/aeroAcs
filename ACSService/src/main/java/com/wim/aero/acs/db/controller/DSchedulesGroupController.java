package com.wim.aero.acs.db.controller;

import com.wim.aero.acs.db.service.impl.DSchedulesGroupDetailServiceImpl;
import com.wim.aero.acs.model.request.ScpRequestInfo;
import com.wim.aero.acs.model.result.RespCode;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.protocol.timezone.TimeZone;
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
 * @since 2022-03-22
 */
@RestController
@RequestMapping("/db/dSchedulesGroup")
public class DSchedulesGroupController {

    private final DSchedulesGroupDetailServiceImpl service;

    @Autowired
    public DSchedulesGroupController(DSchedulesGroupDetailServiceImpl service) {
        this.service = service;
    }

    @RequestMapping(value = "/timezone", method = {RequestMethod.POST})
    public ResultBean<List<String>> getTimeZone(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();

        List<TimeZone> list = service.getTimeZones(scpId);
        list.forEach(System.out :: println);

        return ResultBeanUtil.makeOkResp();
    }



}
