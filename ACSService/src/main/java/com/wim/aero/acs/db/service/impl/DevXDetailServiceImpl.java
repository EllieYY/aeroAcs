package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.DevXDetail;
import com.wim.aero.acs.db.mapper.DevXDetailMapper;
import com.wim.aero.acs.db.service.DevXDetailService;
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
public class DevXDetailServiceImpl extends ServiceImpl<DevXDetailMapper, DevXDetail> implements DevXDetailService {
    public List<DevXDetail> getByScpId(int scpId) {
        return this.baseMapper.selectAllByControllerId(scpId);
    }

    public List<Integer> getPortBySioModel(int scpId, List<Integer> modelList) {
        return this.baseMapper.selectControllerPortByControllerIdAndModel(scpId, modelList);
    }


    public boolean isVBord(int scpId, int sioId) {
        int model = this.baseMapper.selectModelByControllerIdAndSioNumber(scpId, sioId);
        if (model >= 190 && model <= 192) {
            return true;
        }

        return false;
    }
}
