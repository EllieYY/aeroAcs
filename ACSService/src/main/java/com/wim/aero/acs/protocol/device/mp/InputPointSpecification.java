package com.wim.aero.acs.protocol.device.mp;

import com.wim.aero.acs.db.entity.DevInputDetail;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * icvtNum (conversionTable)
 * 常开         0 Normally closed, no End-Of-Line (EOL)
 * 常关         1 Normally open, no EOL
 * 1k正常 2k报警 2 Standard (ROM’ed) EOL: 1kΩ normal, 2kΩ active
 * 2k正常 1k报警 3 Standard (ROM’ed) EOL: 2kΩ normal, 1kΩ active
 * 非常规报警类型，可以通过1101来配置
 *
 * debounce: 0 ~ 15, scan period is 16.7
 *
 * The last three inputs on the SIO are Tamper, AC Fail, and Battery Fail.
 *      * X1100, X100: Input 4,5,6
 *      * X200: Input 16,17,18
 *      * X300: Input 2,3,4
 * 防撬报警、电源故障报警、蓄电池不足报警
 */

/**
 * @title: InputPointSpecification
 * @author: Ellie
 * @date: 2022/03/11 13:12
 * @description: 6.5 Command 110: Input Point Specification
 **/
@Data
public class InputPointSpecification extends Operation {
    @CmdProp(index = 2)
    private Integer lastModified = 0;

    @CmdProp(index = 3)
    private Integer scpNumber;

    @CmdProp(index = 4)
    private Integer sioNumber;

    @CmdProp(index = 5)
    private Integer input; // 0 ~ nInputs-1 (Command 109)

    @CmdProp(index = 6, defaultValue = "1")
    private Integer icvtNum;

    @CmdProp(index = 7, defaultValue = "4")
    private Integer debounce = 4;  // Recommended setting for REX is 2, and 4-6 for standard input

    @CmdProp(index = 8, defaultValue = "0")
    private Integer holdTime = 0;  // 2 ~ 15

    public static InputPointSpecification fromDb(DevInputDetail detail) {
        InputPointSpecification result = new InputPointSpecification();
        result.setScpNumber(detail.getControllerId());
        result.setSioNumber(detail.getSioNumber());
        result.setInput(detail.getInput());

        if (StringUtils.hasText(detail.getInMode())) {
            result.setIcvtNum(Integer.parseInt(detail.getInMode()));
        }

        return result;
    }
}
