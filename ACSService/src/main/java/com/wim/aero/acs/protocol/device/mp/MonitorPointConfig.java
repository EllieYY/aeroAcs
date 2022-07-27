package com.wim.aero.acs.protocol.device.mp;

import com.wim.aero.acs.db.entity.DevInputDetail;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @title: MpSpecification
 * @author: Ellie
 * @date: 2022/03/11 16:14
 * @description: 对应输入点的配置，7.2 Command 113: Monitor Point Configuration
 **/
@Data
public class MonitorPointConfig extends Operation {

    @CmdProp(index = 2)
    private Integer lastModified = 0;

    @CmdProp(index = 3)
    private Integer scpNumber;

    @CmdProp(index = 4)
    private Integer mpNumber;  // 0 to nMp-1 (Command 1107)

    @CmdProp(index = 5)
    private Integer sioNumber;   // -1 removes physical link to input point.

    @CmdProp(index = 6)
    private Integer inputNumber;

    /**
     * Define   Value    Log function code meaning
     * MPLG_0   0x00     Logs all changes
     * MPLG_1   0x01     Do not log contact change-of-state if masked
     * MPLG_2   0x02     Do not log contact change-of-state if masked and no fault-to-fault changes
     */
    @CmdProp(index = 7, defaultValue = "1")
    private Integer logFuncCode;

    /**
     * 0 = Normal mode (no exit or entry delay)
     * 1 = Non-latching mode - 适用开门和关门延迟
     * 2 = Latching mode   封闭型继电器
     */
    @CmdProp(index = 8)
    private Integer mode = 0;

    /**
     * 0 - no delay
     * max - 65535 seconds
     */
    @CmdProp(index = 9)
    private Integer delayEntry = 0;

    @CmdProp(index = 10)
    private Integer delayExit = 0;

    public static MonitorPointConfig fromDb(DevInputDetail detail) {
        MonitorPointConfig result = new MonitorPointConfig();
        result.setScpNumber(detail.getControllerId());
        result.setSioNumber(detail.getSioNumber());
        result.setInputNumber(detail.getInput());
        result.setMpNumber(detail.getMpNumber());

        if (StringUtils.hasText(detail.getAlarmDelayFlag())) {
            int mode = Integer.parseInt(detail.getAlarmDelayFlag());
            if (mode != 0) {
                mode = 1;
                result.setMode(mode);
                result.setDelayEntry(detail.getInDelay());
                result.setDelayExit(detail.getOutDelay());
            }
        }

        return result;
    }
}
