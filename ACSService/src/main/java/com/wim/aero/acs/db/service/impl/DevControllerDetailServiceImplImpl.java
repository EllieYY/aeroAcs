package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.DevControllerDetail;
import com.wim.aero.acs.db.mapper.DevControllerDetailMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ellie
 * @since 2022-03-21
 */
@Service
public class DevControllerDetailServiceImplImpl extends ServiceImpl<DevControllerDetailMapper, DevControllerDetail> implements com.wim.aero.acs.db.service.DevControllerDetailServiceImpl {
    public DevControllerDetail getScpConfiguration(int scpId) {
        List<DevControllerDetail> devList = this.baseMapper.selectAllByDeviceId(scpId);
        if (devList.size() > 0) {
            return devList.get(0);
        }

        // TODO:返回空，不合适，找其他方式
        return null;
    }

}
