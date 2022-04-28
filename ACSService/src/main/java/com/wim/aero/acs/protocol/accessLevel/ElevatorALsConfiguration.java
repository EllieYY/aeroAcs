package com.wim.aero.acs.protocol.accessLevel;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.model.db.ACRTz;
import com.wim.aero.acs.model.db.EleAccessLevelInfo;
import com.wim.aero.acs.model.db.EleTz;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: ElevatorALsConfiguration
 * @author: Ellie
 * @date: 2022/03/11 11:27
 * @description: 电梯访问权限参数配置 - 16.6 Command 502: Elevator Access Level Configuration
 **/
@Data
public class ElevatorALsConfiguration extends Operation {
    @CmdProp(index = 2)
    private Integer lastModified = 0;

    @CmdProp(index = 3)
    private Integer scpNumber;

    /** 电梯权限编号 */
    @CmdProp(index = 4)
    private Integer eleNumber;

    /** 时间组，每层对应一个，索引从0开始，要填满128个字段 */
    @CmdProp(index = 5, enCodec = "formatList")
    private List<Integer> tz;

    public static ElevatorALsConfiguration fromDb(EleAccessLevelInfo info) {
        ElevatorALsConfiguration result = new ElevatorALsConfiguration();
        result.setScpNumber(info.getNScpNumber());
        result.setEleNumber(info.getEleNumber());

        // 初始化0
        List<Integer> tzList = new ArrayList<>();
        for (int i = 0; i < 128; i++) {
            tzList.add(0);
        }

        // 对应读写器编号位置填对应时间组编号
        List<EleTz> eleTzList = info.getTzList();
        for(EleTz item:eleTzList) {
            int tzNo = item.getTz();
            int floor = item.getFloor();
            if (floor >= 0 && floor < 128) {
                tzList.set(floor, tzNo);
            }
        }
        result.setTz(tzList);

        return result;
    }
}
