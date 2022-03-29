package com.wim.aero.acs.protocol.device;

import com.wim.aero.acs.db.entity.DevOutputDetail;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;


/**
 * @title: CpSpecification
 * @author: Ellie
 * @date: 2022/03/11 16:26
 * @description: 对应输出点的配置。7.3 Command 114: Control Point Configuration
 **/
@Data
public class ControlPointConfig extends Operation {
    @CmdProp(index = 2)
    private Integer lastModified = 0;

    @CmdProp(index = 3)
    private Integer scpNumber;

    @CmdProp(index = 4)
    private Integer cpNumber; // 0 to nCp-1 (Command 1107)

    @CmdProp(index = 5)
    private Integer sioNumber; // -1 removes physical link to output point.

    @CmdProp(index = 6)
    private Integer ouputNumber;

    @CmdProp(index = 7)
    private Integer dfltPulse;     // 脉冲次数，每秒一次

    public static  ControlPointConfig fromDb(DevOutputDetail detail) {
        ControlPointConfig result = new ControlPointConfig();
        result.setScpNumber(detail.getControllerId());
        result.setSioNumber(detail.getPDeviceId());
        result.setOuputNumber(detail.getOutput());
        result.setCpNumber(detail.getCpNumber());
        result.setDfltPulse(detail.getPulseCycle());
        return result;
    }
}
