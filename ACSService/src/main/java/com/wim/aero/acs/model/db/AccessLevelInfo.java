package com.wim.aero.acs.model.db;

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
    private String nEscortCode;

    private List<ACRTz> tzList;

}
