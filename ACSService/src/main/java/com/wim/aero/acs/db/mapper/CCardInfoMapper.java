package com.wim.aero.acs.db.mapper;
import java.util.List;

import com.wim.aero.acs.protocol.card.CardAdd;
import org.apache.ibatis.annotations.Param;

import com.wim.aero.acs.db.entity.CCardInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ellie
 * @since 2022-03-22
 */
@Mapper
public interface CCardInfoMapper extends BaseMapper<CCardInfo> {
//    List<CardAdd> selectAllByCardNo(@Param("list") List<String> cardNoList);

//    List<CardAdd> selectAllByAccessLevels(@Param("list") List<Integer> alList);

    /** 通过控制器id找卡 -- 普通控制器 */
    List<CardAdd> selectAllByScpId(@Param("scpId") Integer scpId) throws DataAccessException;

    /** 通过控制器id找卡 -- 电梯控制器 */
    List<CardAdd> selectAllByEleScpId(@Param("scpId") Integer scpId) throws DataAccessException;

    /** 通过卡号找授权信息 -- 普通控制器 */
    List<CardAdd> selectAllByCardList(@Param("list") List<String> list) throws DataAccessException;

    /** 通过卡号找授权信息 -- 电梯控制器 */
    List<CardAdd> selectAllByCardListForEle(@Param("list") List<String> list) throws DataAccessException;

    List<Integer> selectScpIdsByCardNo(@Param("list") List<String> list);
}
