package com.wim.aero.acs.service;

import com.wim.aero.acs.model.scp.ScpShadow;
import com.wim.aero.acs.model.scp.ScpStatus;
import com.wim.aero.acs.model.scp.SeqNoInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    /** scpId:ScpShadow */
    static private Map<Integer, ScpShadow> scpMap = new ConcurrentHashMap<>();

    /** scpId:seqNo */
    static private Map<Integer, SeqNoInfo> scpSeqNoMap = new ConcurrentHashMap<>();

    @Autowired
    ScpService scpService;
    @Autowired
    static ScpService conScpService;
    @Autowired
    SioService sioService;
    @Autowired
    static SioService conSioService;

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
            log.info("addScp - {}", scpId);
            ScpShadow scpShadow = new ScpShadow(scpId, ScpStatus.INIT);
            scpMap.put(scpId, scpShadow);
        }
    }


    // 删除scp
    public static void deleteScp(int scpId) {
        if (scpMap.containsKey(scpId)) {
            scpMap.remove(scpId);
        }

        log.info("deleteScp - {}", scpId);
    }

    public static void scpOnline(int scpId) {
        if (scpMap.containsKey(scpId)) {
            ScpShadow scpShadow = scpMap.get(scpId);
            ScpStatus preStatus = scpShadow.getState();
            scpShadow.setState(ScpStatus.ON_LINE);
            scpMap.put(scpId, scpShadow);
        } else {
            ScpShadow scpShadow = new ScpShadow(scpId, ScpStatus.ON_LINE);
            scpMap.put(scpId, scpShadow);
        }

        // 更新状态
        conScpService.scpOnlineStateNotify(scpId, ScpStatus.ON_LINE.getStatus());
    }


    public static void scpOffline(int scpId) {
        if (scpMap.containsKey(scpId)) {
            ScpShadow scpShadow = scpMap.get(scpId);
            scpShadow.setState(ScpStatus.OFF_LINE);
            scpMap.put(scpId, scpShadow);
        }

        // 更新状态
        conScpService.scpOnlineStateNotify(scpId, ScpStatus.OFF_LINE.getStatus());
    }

    /** 更新控制器的transaction索引 */
    public static void updateTR(int scpId, long oldest, long lastRprtd) {
        if (scpMap.containsKey(scpId)) {
            ScpShadow scpShadow = scpMap.get(scpId);
            scpShadow.setOldest(oldest);
            scpShadow.setLastRprtd(lastRprtd);
            scpMap.put(scpId, scpShadow);

            // TODO:后续处理
        }
    }


    /** 事件提取 - 更新控制器的transaction索引 */
    public void updateSeqNoInfo(int scpId, long start, long end, long cur) {
        if (scpSeqNoMap.containsKey(scpId)) {
            SeqNoInfo seqNoInfo = scpSeqNoMap.get(scpId);
            seqNoInfo.setExtractStart(start);
            seqNoInfo.setExtractEnd(end);
            seqNoInfo.setExtractMax(cur);
            scpSeqNoMap.put(scpId, seqNoInfo);
            log.info("[{} - 事件提取] {}", scpId, seqNoInfo.toString());
        } else {
            SeqNoInfo seqNoInfo = new SeqNoInfo(scpId, start, end, cur);
            scpSeqNoMap.put(scpId, seqNoInfo);
            log.info("[{} - 事件提取] {}", scpId, seqNoInfo.toString());
        }
    }

    /**
     * 判断事件是否需要拦截
     * @return
     */
    public boolean needIntercept(int scpId, long seqNo) {
        if (scpSeqNoMap.containsKey(scpId)) {
            SeqNoInfo seqNoInfo = scpSeqNoMap.get(scpId);
            long seqMin = seqNoInfo.getExtractEnd();
            long seqMax = seqNoInfo.getExtractMax();
            if (seqNo > seqMin && seqNo <= seqMax) {
                return true;
            }
        }

        return false;
    }
}
