package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.DAccessLevelDoor;
import com.wim.aero.acs.db.mapper.DAccessLevelDoorMapper;
import com.wim.aero.acs.db.service.DAccessLevelDoorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wim.aero.acs.model.db.AccessLevelInfo;
import com.wim.aero.acs.model.db.EleAccessLevelInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ellie
 * @since 2022-03-22
 */
@Service
public class DAccessLevelDoorServiceImpl extends ServiceImpl<DAccessLevelDoorMapper, DAccessLevelDoor> implements DAccessLevelDoorService {
    public List<AccessLevelInfo> getByScpId(int scpId) {
        return this.baseMapper.selectAllByControllerId(scpId);
    }

    public List<AccessLevelInfo> getListByScpId(int scpId, List<Integer> alvList) {
        return this.baseMapper.selectListByControllerId(scpId, alvList);
    }

    public List<AccessLevelInfo> getByScpIdForEle(int scpId) {
        return this.baseMapper.selectAllByControllerIdForEle(scpId);
    }

    public List<AccessLevelInfo> getListByScpIdForEle(int scpId, List<Integer> alvList) {
        return this.baseMapper.selectListByControllerIdForEle(scpId, alvList);
    }

    public List<EleAccessLevelInfo> getEleLevelByScp(int scpId) {
        return this.baseMapper.selectEleLevelByScpId(scpId);
    }

    public List<Integer> getALsByScpId(int scpId) {
        return this.baseMapper.searchAccessLevelIdByControllerId(scpId);
    }
}
