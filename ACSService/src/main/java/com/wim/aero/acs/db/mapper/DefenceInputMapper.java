package com.wim.aero.acs.db.mapper;
import java.util.List;

import com.wim.aero.acs.protocol.device.mp.MpGroupSpecification;
import org.apache.ibatis.annotations.Param;

import com.wim.aero.acs.db.entity.DefenceInput;
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
public interface DefenceInputMapper extends BaseMapper<DefenceInput> {
    List<MpGroupSpecification> selectAllByControllerId(@Param("controllerId") Integer controllerId);
}
