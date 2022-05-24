package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.TriggerInfo;
import com.wim.aero.acs.db.mapper.TriggerInfoMapper;
import com.wim.aero.acs.db.service.TriggerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wim.aero.acs.model.db.TriggerInfoEx;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ellie
 * @since 2022-05-23
 */
@Service
public class TriggerInfoServiceImpl extends ServiceImpl<TriggerInfoMapper, TriggerInfo> implements TriggerInfoService {
    public List<TriggerInfoEx> getTriggerInfoForScp(int scpId) {
        return this.baseMapper.selectAllByControllerId(scpId);
    }

}
