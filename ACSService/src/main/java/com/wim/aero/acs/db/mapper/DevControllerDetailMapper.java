package com.wim.aero.acs.db.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wim.aero.acs.db.entity.DevControllerDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ellie
 * @since 2022-03-24
 */
@Mapper
public interface DevControllerDetailMapper extends BaseMapper<DevControllerDetail> {
    List<DevControllerDetail> selectAllByDeviceId(@Param("deviceId") Integer deviceId);
}
