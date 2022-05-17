package com.wim.aero.acs.model.request;

import com.wim.aero.acs.db.entity.TaskDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title: AlvlRequestInfo
 * @author: Ellie
 * @date: 2022/05/17 14:30
 * @description:
 **/
@Data
@ApiModel(value = "访问级别请求参数")
public class AlvlRequestInfo extends TaskRequest {
    @ApiModelProperty(value = "控制器id")
    private int scpId;

    @ApiModelProperty(value = "是否梯控")
    private boolean isEle;
}
