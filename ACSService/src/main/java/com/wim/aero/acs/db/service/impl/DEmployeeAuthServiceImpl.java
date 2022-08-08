package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.DEmployeeAuth;
import com.wim.aero.acs.db.mapper.DEmployeeAuthMapper;
import com.wim.aero.acs.db.service.DEmployeeAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ellie
 * @since 2022-08-01
 */
@Service
public class DEmployeeAuthServiceImpl extends ServiceImpl<DEmployeeAuthMapper, DEmployeeAuth> implements DEmployeeAuthService {
    public DEmployeeAuth getOneByScpIdAndCard(int scpId, String cardNo) {
        return this.baseMapper.selectOneByCardNoAndControllerId(cardNo, scpId);
    }
}
