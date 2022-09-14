package com.wim.aero.acs.protocol.accessLevel;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.model.db.ACRTz;
import com.wim.aero.acs.model.db.AccessLevelInfo;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.ArrayList;
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

    @CmdProp(index = 5, defaultValue = "0")
    private int operMode = 0;

    //6-69 tz
    @CmdProp(index = 6, enCodec = "formatList")
    List<Integer> tz; // Time zone number for each ACR (up to 64 ACRs). Set the time zone to 0 to never enable access

    public static AccessLevelExtended fromDb(AccessLevelInfo info) {
        AccessLevelExtended result = new AccessLevelExtended();
        result.setScpNumber(info.getNScpNumber());
        result.setAlvlNumber(info.getNAlvlnumber());

        // 初始化0
        List<Integer> tzList = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            tzList.add(0);
        }

        // 对应读写器编号位置填对应时间组编号
        List<ACRTz> acrTzList = info.getTzList();
        for(ACRTz item:acrTzList) {
            if (item.getState() != 0) {
                continue;
            }
            int tzNo = item.getTz();
            int acrNo = item.getAcrId();
            if (acrNo >= 0 && acrNo < 64) {
                tzList.set(acrNo, tzNo);
            }
        }
        result.setTz(tzList);

        return result;
    }
}
