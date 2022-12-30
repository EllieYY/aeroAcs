package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.DevReaderDetail;
import com.wim.aero.acs.db.mapper.DevReaderDetailMapper;
import com.wim.aero.acs.db.service.DevReaderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wim.aero.acs.model.db.AcrStrikeInfo;
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
public class DevReaderDetailServiceImpl extends ServiceImpl<DevReaderDetailMapper, DevReaderDetail> implements DevReaderDetailService {
    /**
     * 按上级设备查找读卡器
     * @param ids
     * @return
     */
    public List<DevReaderDetail> getByPDeviceIds(List<Integer> ids) {
        return this.baseMapper.selectAllByPDeviceId(ids);
    }

    /**
     * 通过scpId查找
     * @param scpId
     * @return
     */
    public List<DevReaderDetail> getByScpId(int scpId) {
        return this.baseMapper.selectAllByControllerId(scpId);
    }

    /**
     * 通过scpId查找
     * @param scpId
     * @return
     */
    public List<DevReaderDetail> getByScpIdAndSio(int scpId, int sioId) {
        return this.baseMapper.selectAllByControllerIdAndSioNumber(scpId, sioId);
    }

    /**
     * 查找锁的信息
     * @param deviceId
     * @return
     */
    public AcrStrikeInfo getAcrStrike(int deviceId) {
        return this.baseMapper.selectStrikeByDeviceId(deviceId);
    }

    /**
     * 判断输入点是否已配置
     * @return
     */
    public boolean isInputConfigured(int scpId, int sioId, int inputNo) {
        return this.baseMapper.CountByInputInfo(scpId, sioId, inputNo) > 0;
    }


    /**
     * 判断输出点是否已配置
     * @return
     */
    public boolean isOutputConfigured(int scpId, int sioId, int outputNo) {
        return this.baseMapper.CountByOutputInfo(scpId, sioId, outputNo) > 0;
    }
}
