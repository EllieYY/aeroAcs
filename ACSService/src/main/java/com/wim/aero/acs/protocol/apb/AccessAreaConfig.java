package com.wim.aero.acs.protocol.apb;

import com.wim.aero.acs.db.entity.Apb;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * @title: AccessAreaConfig
 * @author: Ellie
 * @date: 2022/03/14 09:21
 * @description: 8.3 Command 1121: Configure Access Area
 **/
@Data
public class AccessAreaConfig extends Operation {
    @CmdProp(index = 2)
    private int lastModified = 0;

    @CmdProp(index = 3)
    private int scpNumber;

    @CmdProp(index = 4)
    private int areaNumber; // Access area number (1 to 127)

    /**
     * 0 = Two or more not required in area
     * 1 = Two or more required
     * 2 & 3 = Reserved, do not use
     */
    @CmdProp(index = 5)
    private int multiOccupancy;

    // 0 = NOP, 1 = Disable area, 2 = Enable area
    @CmdProp(index = 6)
    private int accessControl;

    //0 = Do not change current occupancy count
    //1 = Change current occupancy to occ_set
    //2 = Reserved, do not use
    @CmdProp(index = 7)
    private int occControl;

    @CmdProp(index = 8)
    private int occSet; // Change current occupancy count to this value if occ_control is non-zero (long)

    @CmdProp(index = 9)
    private int occMax; // Maximum occupancy level (long)

    @CmdProp(index = 10)
    private int occUp; // Log transaction when this count is reached, counting up (long)

    @CmdProp(index = 11)
    private int occDown; // Log transaction when this count is reached, counting down (long)

    // deprecated.
    @CmdProp(index = 12)
    private int occSetSpc = 0;
    @CmdProp(index = 13)
    private int custodian = 0;
    @CmdProp(index = 14)
    private int nAppRqRlySio = 0;
    @CmdProp(index = 15)
    private int nAppRqRlyNum = 0;
    @CmdProp(index = 16)
    private int nAppRqRlyDly = 0;
    @CmdProp(index = 17)
    private int areaFlags = 0;

    public static AccessAreaConfig fromDb(Apb src) {
        AccessAreaConfig result = new AccessAreaConfig();
        result.setScpNumber(src.getControllerId());
        result.setAreaNumber(src.getApbId());
        result.setMultiOccupancy(Integer.parseInt(src.getApbRule()));
        result.setAccessControl(Integer.parseInt(src.getCloseFlag()));
        result.setOccSet(src.getCurrNumPerson());
        result.setOccMax(src.getMaxNumPerson());
        result.setOccUp(src.getMinNumEvent());
        result.setOccDown(src.getMaxNumEvent());

        return result;
    }
}
