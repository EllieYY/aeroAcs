package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.DevOutputDetail;
import com.wim.aero.acs.db.mapper.DevOutputDetailMapper;
import com.wim.aero.acs.db.service.DevOutputDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class DevOutputDetailServiceImpl extends ServiceImpl<DevOutputDetailMapper, DevOutputDetail> implements DevOutputDetailService {
    /**
     * 按上级设备查找输出点
     * @param ids
     * @return
     */
    public List<DevOutputDetail> getByPDeviceIds(List<Integer> ids) {
        return this.baseMapper.selectAllByPDeviceId(ids);
    }

    /**
     * 通过scpId查找
     * @param scpId
     * @return
     */
    public List<DevOutputDetail> getByScpId(int scpId) {
        return this.baseMapper.selectAllByControllerId(scpId);
    }
}
