package com.wim.aero.acs.db.mapper;
import java.util.List;

import com.wim.aero.acs.model.db.TriggerInfoEx;
import org.apache.ibatis.annotations.Param;

import com.wim.aero.acs.db.entity.TriggerInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ellie
 * @since 2022-05-23
 */
@Mapper
public interface TriggerInfoMapper extends BaseMapper<TriggerInfo> {
    List<TriggerInfoEx> selectAllByControllerId(@Param("controllerId") Integer controllerId);

}
