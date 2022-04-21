package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.CCardInfo;
import com.wim.aero.acs.db.mapper.CCardInfoMapper;
import com.wim.aero.acs.db.service.CCardInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wim.aero.acs.protocol.card.CardAdd;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ellie
 * @since 2022-03-22
 */
@Service
public class CCardInfoServiceImpl extends ServiceImpl<CCardInfoMapper, CCardInfo> implements CCardInfoService {
    public List<CardAdd> getByCardNo(List<String> cardNoList) {
        return this.baseMapper.selectAllByCardNo(cardNoList);
    }

    public List<CardAdd> getByAccessLevels(List<Integer> alList) {
        return this.baseMapper.selectAllByAccessLevels(alList);
    }

    public List<CardAdd> getByScpId(int scpId) {
        return this.baseMapper.selectAllByScpId(scpId);
    }

    public List<CardAdd> getByCardList(List<String> cardList) {
        return this.baseMapper.selectAllByCardList(cardList);
    }

}
