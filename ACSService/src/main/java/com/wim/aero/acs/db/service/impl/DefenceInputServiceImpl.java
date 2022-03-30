package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.DefenceInput;
import com.wim.aero.acs.db.mapper.DefenceInputMapper;
import com.wim.aero.acs.db.service.DefenceInputService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wim.aero.acs.protocol.device.mp.MpGroupSpecification;
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
public class DefenceInputServiceImpl extends ServiceImpl<DefenceInputMapper, DefenceInput> implements DefenceInputService {
    public List<MpGroupSpecification> getByScpId(int scpId) {
        return this.baseMapper.selectAllByControllerId(scpId);
    }
}
