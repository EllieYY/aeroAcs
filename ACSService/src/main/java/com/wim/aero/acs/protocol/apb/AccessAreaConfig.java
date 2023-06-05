package com.wim.aero.acs.protocol.apb;

import com.wim.aero.acs.db.entity.Apb;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @title: AccessAreaConfig
 * @author: Ellie
 * @date: 2022/03/14 09:21
 * @description: 8.3 Command 1121: Configure Access Area
 **/
@Data
public class AccessAreaConfig extends Operation {
    @CmdProp(index = 2)
    private Integer lastModified = 0;

    @CmdProp(index = 3)
    private Integer scpNumber;

    @CmdProp(index = 4)
    private Integer areaNumber; // Access area number (1 to 127)

    /**
     * 0 = Two or more not required in area
     * 1 = Two or more required
     * 2 & 3 = Reserved, do not use
     */
    @CmdProp(index = 5)
    private Integer multiOccupancy;

    // 0 = NOP, 1 = Disable area, 2 = Enable area
    @CmdProp(index = 6)
    private Integer accessControl;

    //0 = Do not change current occupancy count
    //1 = Change current occupancy to occ_set
    //2 = Reserved, do not use
    @CmdProp(index = 7)
    private Integer occControl = 1;

    @CmdProp(index = 8)
    private Integer occSet; // Change current occupancy count to this value if occ_control is non-zero (long)

    @CmdProp(index = 9)
    private Integer occMax; // Maximum occupancy level (long)

    @CmdProp(index = 10)
    private Integer occUp; // Log transaction when this count is reached, counting up (long)

    @CmdProp(index = 11)
    private Integer occDown; // Log transaction when this count is reached, counting down (long)

    // deprecated.
    @CmdProp(index = 12)
    private Integer occSetSpc = 0;
    @CmdProp(index = 13)
    private Integer custodian = 0;
    @CmdProp(index = 14)
    private Integer nAppRqRlySio = 0;
    @CmdProp(index = 15)
    private Integer nAppRqRlyNum = 0;
    @CmdProp(index = 16)
    private Integer nAppRqRlyDly = 0;
    @CmdProp(index = 17)
    private Integer areaFlags = 0;

    public static AccessAreaConfig fromDb(Apb src) {
        AccessAreaConfig result = new AccessAreaConfig();
        result.setScpNumber(src.getControllerId());
        result.setAreaNumber(src.getScpApbId());

        if (StringUtils.hasText(src.getApbRule())) {
            result.setMultiOccupancy(Integer.parseInt(src.getApbRule()));
        }
        if (StringUtils.hasText(src.getCloseFlag())) {
            result.setAccessControl(Integer.parseInt(src.getCloseFlag()));
        }
        result.setOccSet(src.getCurrNumPerson());
        result.setOccMax(src.getMaxNumPerson());
        result.setOccUp(src.getMinNumEvent());
        result.setOccDown(src.getMaxNumEvent());

        result.setAreaFlags(src.getAreaFlag());

        return result;
    }
}
