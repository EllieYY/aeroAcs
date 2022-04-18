package com.wim.aero.acs.service;

import com.wim.aero.acs.model.scp.ScpShadow;
import com.wim.aero.acs.model.scp.ScpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private Map<Integer, ScpShadow> scpMap = new ConcurrentHashMap<>();

    // 添加scp
    public void addScp(int scpId) {
        if (scpMap.containsKey(scpId)) {
            ScpShadow scpShadow = scpMap.get(scpId);
            scpShadow.setState(ScpStatus.UNKNOWN);
            scpMap.put(scpId, scpShadow);
        } else {
            ScpShadow scpShadow = new ScpShadow(scpId, ScpStatus.UNKNOWN);
            scpMap.put(scpId, scpShadow);
        }
    }

    public void scpOnline(int scpId) {
        if (scpMap.containsKey(scpId)) {
            ScpShadow scpShadow = scpMap.get(scpId);
            scpShadow.setState(ScpStatus.ON_LINE);
            scpMap.put(scpId, scpShadow);
        }
    }


    public void scpOffline(int scpId) {
        if (scpMap.containsKey(scpId)) {
            scpMap.remove(scpId);
        }
    }
}
