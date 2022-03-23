package com.wim.aero.acs.protocol.accessLevel;

import com.wim.aero.acs.message.Operation;
import lombok.Data;

import java.util.List;

/**
 * @title: ElevatorALsConfiguration
 * @author: Ellie
 * @date: 2022/03/11 11:27
 * @description: 电梯访问权限参数配置 - 16.6 Command 502: Elevator Access Level Configuration
 **/
@Data
public class ElevatorALsConfiguration extends Operation {
    private int lastModified = 0;
    private int scpNumber;

    /** 电梯权限编号 */
    private int ealNumber;

    /** 时间组，每层对应一个，索引从0开始，要填满128个字段 */
    private List<Integer> tz;
}
