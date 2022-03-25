package com.wim.aero.acs.protocol.accessLevel;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.model.ACRTz;
import com.wim.aero.acs.model.AccessLevelInfo;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @title: AccessLevelExtended
 * @author: Ellie
 * @date: 2022/03/14 14:14
 * @description: 指定读写器的访问级别设置。10.5 Command 2116: Access Level Configuration Extended
 **/
@Data
public class AccessLevelExtended extends Operation {
    @CmdProp(index = 2)
    private int lastModified = 0;

    @CmdProp(index = 3)
    private int scpNumber;

    @CmdProp(index = 4)
    private int alvlNumber;

    @CmdProp(index = 5)
    private int operMode = 0;

    //6-69 tz
    @CmdProp(index = 6)
    List<Integer> tz; // Time zone number for each ACR (up to 64 ACRs). Set the time zone to 0 to never enable access

    public static AccessLevelExtended fromDb(AccessLevelInfo info) {
        AccessLevelExtended result = new AccessLevelExtended();
        result.setScpNumber(info.getNScpNumber());
        result.setAlvlNumber(info.getNAlvlnumber());

        List<ACRTz> acrTzList = info.getTzList();
        List<Integer> tzList = Arrays.asList(new Integer[64]);
        for (Integer item:tzList) {

        }



        return result;
    }
}
