package com.wim.aero.acs.db.service.impl;

import com.wim.aero.acs.db.entity.TaskDetail;
import com.wim.aero.acs.db.mapper.TaskDetailMapper;
import com.wim.aero.acs.db.service.TaskDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ellie
 * @since 2022-04-21
 */
@Service
public class TaskDetailServiceImpl extends ServiceImpl<TaskDetailMapper, TaskDetail> implements TaskDetailService {

    public void updateTaskState(String status, Date msgReturnTime, String uid) {
        this.baseMapper.updateStatusAndMsgReturnTimeByUid(status, msgReturnTime, uid);
    }

    public void updateTaskStateBatch(List<TaskDetail> detailList) {
        if (detailList.size() <= 0) {
            return;
        }
        this.baseMapper.updateStatusBatch(detailList);
    }


}
