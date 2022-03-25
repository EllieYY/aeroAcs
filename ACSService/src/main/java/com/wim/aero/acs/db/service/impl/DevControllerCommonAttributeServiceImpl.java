package com.wim.aero.acs.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wim.aero.acs.db.entity.DevControllerCommonAttribute;
import com.wim.aero.acs.db.mapper.DevControllerCommonAttributeMapper;
import com.wim.aero.acs.db.service.DevControllerCommonAttributeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ellie
 * @since 2022-03-24
 */
@Service
public class DevControllerCommonAttributeServiceImpl extends ServiceImpl<DevControllerCommonAttributeMapper, DevControllerCommonAttribute> implements DevControllerCommonAttributeService {
    public DevControllerCommonAttribute getADSpecification() {
        return this.baseMapper.selectTopOne();
    }
}
