package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.DAccessLevelDoor;
import com.wim.aero.acs.db.mapper.DAccessLevelDoorMapper;
import com.wim.aero.acs.db.service.DAccessLevelDoorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wim.aero.acs.model.AccessLevelInfo;
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
}
