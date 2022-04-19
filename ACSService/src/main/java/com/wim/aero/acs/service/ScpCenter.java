package com.wim.aero.acs.service;

import com.wim.aero.acs.model.scp.ScpShadow;
import com.wim.aero.acs.model.scp.ScpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @title: ScpCenter
 * @author: Ellie
 * @date: 2022/04/18 14:04
 * @description:
 **/
@Slf4j
@Service
public class ScpCenter {
    /** scpId:ScpShadow*/
    static private Map<Integer, ScpShadow> scpMap = new ConcurrentHashMap<>();

    @Autowired
    ScpService scpService;
    @Autowired
    static ScpService conScpService;
    @Autowired
    SIOService sioService;
    @Autowired
    static SIOService conSioService;

    @PostConstruct
    public void init() {
        conScpService = scpService;
        conSioService = sioService;
    }

    // 添加scp
    public static void addScp(int scpId) {
        if (scpMap.containsKey(scpId)) {
            ScpShadow scpShadow = scpMap.get(scpId);
            scpShadow.setState(ScpStatus.INIT);
            scpMap.put(scpId, scpShadow);
        } else {
            ScpShadow scpShadow = new ScpShadow(scpId, ScpStatus.INIT);
            scpMap.put(scpId, scpShadow);
        }
    }

    public static void scpOnline(int scpId) {
        if (scpMap.containsKey(scpId)) {
            ScpShadow scpShadow = scpMap.get(scpId);
            ScpStatus preStatus = scpShadow.getState();
            scpShadow.setState(ScpStatus.ON_LINE);
            scpMap.put(scpId, scpShadow);

            // 上线后开始配置 -- 先验条件：设备处于初始化状态
            if (preStatus == ScpStatus.INIT || preStatus == ScpStatus.OFF_LINE) {
                conScpService.configScp(scpId);
                conSioService.configSioForScp(scpId);
            }
        }
    }


    public static void scpOffline(int scpId) {
        if (scpMap.containsKey(scpId)) {
            ScpShadow scpShadow = scpMap.get(scpId);
            scpShadow.setState(ScpStatus.OFF_LINE);
            scpMap.put(scpId, scpShadow);
        }
    }

    /** 更新控制器的transaction索引 */
    public static void updateTR(int scpId, long oldest, long lastRprtd) {
        if (scpMap.containsKey(scpId)) {
            ScpShadow scpShadow = scpMap.get(scpId);
            scpShadow.setOldest(oldest);
            scpShadow.setLastRprtd(lastRprtd);
            scpMap.put(scpId, scpShadow);
        }
    }
}
