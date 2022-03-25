package com.wim.aero.acs.model;

import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @title: AccessLevelInfo
 * @author: Ellie
 * @date: 2022/03/25 14:40
 * @description:
 **/
@Data
public class AccessLevelInfo {
    private int nScpNumber;
    private int nAlvlnumber;
    private Date activeDate;
    private Date deactiveDate;
    private int nEscortCode;

    private List<ACRTz> tzList;

}
