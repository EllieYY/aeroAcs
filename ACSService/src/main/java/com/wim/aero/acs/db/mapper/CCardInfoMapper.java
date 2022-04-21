package com.wim.aero.acs.db.mapper;
import java.util.List;

import com.wim.aero.acs.protocol.card.CardAdd;
import org.apache.ibatis.annotations.Param;

import com.wim.aero.acs.db.entity.CCardInfo;
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
public interface CCardInfoMapper extends BaseMapper<CCardInfo> {
    List<CardAdd> selectAllByCardNo(@Param("list") List<String> cardNoList);

    List<CardAdd> selectAllByAccessLevels(@Param("list") List<Integer> alList);

    List<CardAdd> selectAllByScpId(@Param("scpId") Integer scpId);

    List<CardAdd> selectAllByCardList(@Param("list") List<String> list);
}
