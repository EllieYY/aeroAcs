package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.EEventCodeDetailDev;
import com.wim.aero.acs.db.mapper.EEventCodeDetailDevMapper;
import com.wim.aero.acs.db.service.EEventCodeDetailDevService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ellie
 * @since 2022-07-18
 */
@Service
public class EEventCodeDetailDevServiceImpl extends ServiceImpl<EEventCodeDetailDevMapper, EEventCodeDetailDev> implements EEventCodeDetailDevService {
    public List<Integer> getMessageType(Integer eventType, Integer eventTypeCode, Integer eventSourceType, Integer changeState) {
        return this.baseMapper.selectAllByEventTypeAndEventTypeCodeAndEventSourceType(eventType,
                eventTypeCode, eventSourceType, changeState);
    }
}
