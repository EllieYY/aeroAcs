package com.wim.aero.acs.db.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

import com.wim.aero.acs.db.entity.TaskDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ellie
 * @since 2022-04-21
 */
@Mapper
public interface TaskDetailMapper extends BaseMapper<TaskDetail> {

    int updateStatusAndMsgReturnTimeByUid(
            @Param("status") String status,
            @Param("msgReturnTime") Date msgReturnTime,
            @Param("uid") String uid);

    int updateStatusBatch(@Param("list") List<TaskDetail> details);


}
