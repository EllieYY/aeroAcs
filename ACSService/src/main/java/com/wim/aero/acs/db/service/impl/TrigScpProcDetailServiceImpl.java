package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.TrigScpProcDetail;
import com.wim.aero.acs.db.mapper.TrigScpProcDetailMapper;
import com.wim.aero.acs.db.service.TrigScpProcDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ellie
 * @since 2022-05-23
 */
@Service
public class TrigScpProcDetailServiceImpl extends ServiceImpl<TrigScpProcDetailMapper, TrigScpProcDetail> implements TrigScpProcDetailService {
    public List<TrigScpProcDetail> getProcDetailsByScp(int scpId) {
        return this.baseMapper.selectAllByControllerIdOrderByProcIdAndOrderNum(scpId);
    }
}
