package com.wim.aero.acs.db.controller;

import com.wim.aero.acs.db.service.impl.CCardInfoServiceImpl;
import com.wim.aero.acs.db.service.impl.DAccessLevelDoorServiceImpl;
import com.wim.aero.acs.model.db.AccessLevelInfo;
import com.wim.aero.acs.model.request.ScpRequestInfo;
import com.wim.aero.acs.model.result.ResultBean;
import com.wim.aero.acs.model.result.ResultBeanUtil;
import com.wim.aero.acs.protocol.card.CardAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
@RequestMapping("/db/dAccessLevel")
public class DAccessLevelController {
    private final DAccessLevelDoorServiceImpl service;
    private final CCardInfoServiceImpl cardInfoService;

    @Autowired
    public DAccessLevelController(DAccessLevelDoorServiceImpl service, CCardInfoServiceImpl cardInfoService) {
        this.service = service;
        this.cardInfoService = cardInfoService;
    }

    @RequestMapping(value = "/al", method = {RequestMethod.POST})
    public ResultBean<List<String>> getTimeZone(@RequestBody ScpRequestInfo request) {
        int scpId = request.getScpId();

//        // 访问级别详情
//        System.out.println("访问级别详情");
//        List<AccessLevelInfo> list = service.getByScpId(scpId);
//        list.forEach(System.out :: println);
//
//        // 控制器的访问级别
//        List<Integer> alList = service.getALsByScpId(scpId);
//        alList.forEach(System.out :: println);
//
//        // 控制器的卡
//        List<CardAdd> cardAddList = cardInfoService.getByScpId(scpId);
//        System.out.println("控制器找卡");
//        cardAddList.forEach(System.out::println);

        // 卡列表找卡
        System.out.println("卡号列表找卡");
        List<String> cardList = new ArrayList<>();
        cardList.add("testnull");

        List<CardAdd> cardAllList = cardInfoService.getByCardList(cardList);

        cardAllList.forEach(System.out::println);
        return ResultBeanUtil.makeOkResp();
    }

}
