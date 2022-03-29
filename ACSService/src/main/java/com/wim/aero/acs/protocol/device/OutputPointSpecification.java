package com.wim.aero.acs.protocol.device;

import com.wim.aero.acs.db.entity.DevOutputDetail;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * mode
 *   value = DriveMode + (0x10 * Inactive or 0x20 * Active)
 * |-------+------------+--------------+---------------------|
 * | value | Drive mode | Offline mode | Output point state  |
 * |-------+------------+--------------+---------------------|
 * | 0     | Normal     | No change    | No change           |
 * | 1     | Inverted   | No change    | No change           |
 * | 16    | Normal     | Inactive     | Relay de-energized  |
 * | 17    | Inverted   | Inactive     | Relay energized     |
 * | 32    | Normal     | Active       | Relay energized     |
 * | 33    | Inverted   | Active       | Relay de-energized  |
 * |-------+------------+--------------+---------------------|
 */

/**
 * @title: OutputPointConfig
 * @author: Ellie
 * @date: 2022/03/11 13:22
 * @description: 6.6 Command 111: Output Point Specification
 **/
@Data
public class OutputPointSpecification extends Operation {
    @CmdProp(index = 2)
    private Integer lastModified = 0;

    @CmdProp(index = 3)
    private Integer scpNumber;

    @CmdProp(index = 4)
    private Integer sioNumber;

    @CmdProp(index = 5)
    private Integer output;  // 0 ~ nOutputs-1 (Command 109)

    @CmdProp(index = 6)
    private Integer mode;

    public static OutputPointSpecification fromDb(DevOutputDetail detail) {
        OutputPointSpecification result = new OutputPointSpecification();
        result.setScpNumber(detail.getControllerId());
        result.setSioNumber(detail.getPDeviceId());
        result.setOutput(detail.getOutput());

        // 暂时只使用值0和1
        if (StringUtils.hasText(detail.getModeFlag())) {
            result.setMode(Integer.parseInt(detail.getModeFlag()));
        }

        return result;
    }
}
