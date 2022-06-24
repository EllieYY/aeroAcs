package com.wim.aero.acs.db.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.wim.aero.acs.db.entity.TriggerVar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ellie
 * @since 2022-06-22
 */
@Mapper
public interface TriggerVarMapper extends BaseMapper<TriggerVar> {

    List<TriggerVar> selectAllByControllerId(@Param("controllerId") Integer controllerId);

}
