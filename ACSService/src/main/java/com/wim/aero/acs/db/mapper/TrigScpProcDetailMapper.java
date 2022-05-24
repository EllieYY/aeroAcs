package com.wim.aero.acs.db.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.wim.aero.acs.db.entity.TrigScpProcDetail;
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
public interface TrigScpProcDetailMapper extends BaseMapper<TrigScpProcDetail> {
    List<TrigScpProcDetail> selectAllByControllerIdOrderByProcIdAndOrderNum(@Param("controllerId") Integer controllerId);
}
