package com.wim.aero.acs.protocol.accessLevel;

import com.wim.aero.acs.message.Operation;
import lombok.Data;

import java.util.List;

/**
 * @title: AccessLevelExtended
 * @author: Ellie
 * @date: 2022/03/14 14:14
 * @description: 指定读写器的访问级别设置。10.5 Command 2116: Access Level Configuration Extended
 **/
@Data
public class AccessLevelExtended extends Operation {
    private int lastModified = 0;
    private int scpNumber;
    private int alvlNumber;
    private int operMode;

    //6-69 tz
    List<Integer> tz; // Time zone number for each ACR (up to 64 ACRs). Set the time zone to 0 to never enable access
}
