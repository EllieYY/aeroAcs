package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.Apb;
import com.wim.aero.acs.db.mapper.ApbMapper;
import com.wim.aero.acs.db.service.ApbService;
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
public class ApbServiceImpl extends ServiceImpl<ApbMapper, Apb> implements ApbService {
    public List<Apb> getByScpId(int scpId) {
        return this.baseMapper.selectAllByControllerId(scpId);
    }
}
