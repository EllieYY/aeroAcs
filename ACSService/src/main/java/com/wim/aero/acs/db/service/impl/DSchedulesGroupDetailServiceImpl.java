package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.DSchedulesGroupDetail;
import com.wim.aero.acs.db.mapper.DSchedulesGroupDetailMapper;
import com.wim.aero.acs.db.service.DSchedulesGroupDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wim.aero.acs.protocol.timezone.TimeZone;
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
public class DSchedulesGroupDetailServiceImpl extends ServiceImpl<DSchedulesGroupDetailMapper, DSchedulesGroupDetail> implements DSchedulesGroupDetailService {
    public List<TimeZone> getTimeZonesByScp(int scpId) {
        return baseMapper.selectByScpId(scpId);
    }
}
