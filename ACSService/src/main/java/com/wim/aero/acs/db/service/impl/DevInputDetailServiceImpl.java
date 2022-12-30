package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.DevInputDetail;
import com.wim.aero.acs.db.mapper.DevInputDetailMapper;
import com.wim.aero.acs.db.service.DevInputDetailService;
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
public class DevInputDetailServiceImpl extends ServiceImpl<DevInputDetailMapper, DevInputDetail> implements DevInputDetailService {

    /**
     * 按上级设备查找输入点
     * @param ids
     * @return
     */
    public List<DevInputDetail> getByPDeviceIds(List<Integer> ids) {
        return this.baseMapper.selectAllByPDeviceIdList(ids);
    }

    /**
     * 按控制器id查找输入点
     * @param scpId
     * @return
     */
    public List<DevInputDetail> getByScpId(int scpId) {
        return this.baseMapper.selectAllByControllerId(scpId);
    }

    /**
     * 按控制器id查找输入点
     * @param scpId
     * @return
     */
    public List<DevInputDetail> getByScpIdAndSioId(int scpId, int sio) {
        return this.baseMapper.selectAllByControllerIdAndSioNumber(scpId, sio);
    }

}
