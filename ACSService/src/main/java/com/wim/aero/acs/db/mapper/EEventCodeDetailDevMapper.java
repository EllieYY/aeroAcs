package com.wim.aero.acs.db.mapper;
import org.apache.ibatis.annotations.Param;

import com.wim.aero.acs.db.entity.EEventCodeDetailDev;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ellie
 * @since 2022-07-18
 */
@Mapper
public interface EEventCodeDetailDevMapper extends BaseMapper<EEventCodeDetailDev> {
//    List<String> getMessageType

    List<Integer> selectAllByEventTypeAndEventTypeCodeAndEventSourceType(
            @Param("eventType") Integer eventType,
            @Param("eventTypeCode") Integer eventTypeCode,
            @Param("eventSourceType") Integer eventSourceType,
            @Param("changeState") Integer changeState);
}
