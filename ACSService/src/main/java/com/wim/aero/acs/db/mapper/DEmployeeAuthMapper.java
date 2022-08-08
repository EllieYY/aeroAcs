package com.wim.aero.acs.db.mapper;
import org.apache.ibatis.annotations.Param;

import com.wim.aero.acs.db.entity.DEmployeeAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ellie
 * @since 2022-08-01
 */
@Mapper
public interface DEmployeeAuthMapper extends BaseMapper<DEmployeeAuth> {
    DEmployeeAuth selectOneByCardNoAndControllerId(@Param("cardNo") String cardNo,
                                                   @Param("controllerId") Integer controllerId);

}
