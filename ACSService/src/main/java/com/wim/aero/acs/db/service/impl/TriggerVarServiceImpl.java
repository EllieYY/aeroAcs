package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.TriggerVar;
import com.wim.aero.acs.db.mapper.TriggerVarMapper;
import com.wim.aero.acs.db.service.TriggerVarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ellie
 * @since 2022-06-22
 */
@Service
public class TriggerVarServiceImpl extends ServiceImpl<TriggerVarMapper, TriggerVar> implements TriggerVarService {
    public List<TriggerVar> getTriVarByScpId(int scpId) {
        return this.baseMapper.selectAllByControllerId(scpId);
    }
}
