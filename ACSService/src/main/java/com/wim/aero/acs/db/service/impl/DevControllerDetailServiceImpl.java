package com.wim.aero.acs.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wim.aero.acs.aop.excption.DataDaoException;
import com.wim.aero.acs.db.entity.DevControllerDetail;
import com.wim.aero.acs.db.mapper.DevControllerDetailMapper;
import com.wim.aero.acs.db.service.DevControllerDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ellie
 * @since 2022-03-24
 */
@Service
public class DevControllerDetailServiceImpl extends ServiceImpl<DevControllerDetailMapper, DevControllerDetail> implements DevControllerDetailService {
    public DevControllerDetail getScpConfiguration(int scpId) {
        List<DevControllerDetail> list = this.baseMapper.selectAllByDeviceId(scpId);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }


    public boolean validScp(int scpId) {
        long count = this.count(new QueryWrapper<DevControllerDetail>()
                        .eq("device_id", scpId)
        );

        return (count > 0);
    }
}
