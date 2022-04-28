package com.wim.aero.acs.db.mapper;
import java.util.List;

import com.wim.aero.acs.model.db.AccessLevelInfo;
import com.wim.aero.acs.model.db.EleAccessLevelInfo;
import org.apache.ibatis.annotations.Param;

import com.wim.aero.acs.db.entity.DAccessLevelDoor;
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
public interface DAccessLevelDoorMapper extends BaseMapper<DAccessLevelDoor> {
    /**
     * 普通控制器访问权限
     * @param controllerId
     * @return
     */
    List<AccessLevelInfo> selectAllByControllerId(@Param("controllerId") Integer controllerId);

    /**
     * 梯控访问权限
     * @param controllerId
     * @return
     */
    List<AccessLevelInfo> selectAllByControllerIdForEle(@Param("controllerId") Integer controllerId);

    /**
     * 电梯级别获取
     * @param controllerId
     * @return
     */
    List<EleAccessLevelInfo> selectEleLevelByScpId(@Param("controllerId") Integer controllerId);


    List<Integer> searchAccessLevelIdByControllerId(@Param("controllerId") Integer controllerId);

}
