package com.wim.aero.acs.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wim.aero.acs.db.entity.DevControllerCommonAttribute;
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
public interface DevControllerCommonAttributeMapper extends BaseMapper<DevControllerCommonAttribute> {
    public DevControllerCommonAttribute selectTopOne();
}
