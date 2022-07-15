package com.wim.aero.acs.db.mapper;
import java.util.List;

import com.wim.aero.acs.protocol.timezone.TimeZone;
import org.apache.ibatis.annotations.Param;

import com.wim.aero.acs.db.entity.DSchedulesGroupDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ellie
 * @since 2022-03-22
 */
@Mapper
public interface DSchedulesGroupDetailMapper extends BaseMapper<DSchedulesGroupDetail> {
    List<TimeZone> selectByScpId(@Param("scpId") int scpId);

    List<TimeZone> selectByScpIdForEle(@Param("scpId") int scpId);

    // 查找所有时间组
    List<TimeZone> selectAllForScp(@Param("scpId") int scpId);

}
