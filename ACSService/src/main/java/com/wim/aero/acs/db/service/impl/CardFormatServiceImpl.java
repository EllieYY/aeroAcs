package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.CardFormat;
import com.wim.aero.acs.db.mapper.CardFormatMapper;
import com.wim.aero.acs.db.service.CardFormatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class CardFormatServiceImpl extends ServiceImpl<CardFormatMapper, CardFormat> implements CardFormatService {
    public List<CardFormat> getCardInfoByIdList(List<Integer> idList) {
        return this.baseMapper.selectAllBySeqNo(idList);
    }
}
