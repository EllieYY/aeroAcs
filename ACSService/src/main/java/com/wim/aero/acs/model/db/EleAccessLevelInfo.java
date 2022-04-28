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
public class EleAccessLevelInfo {
    private int nScpNumber;
    private int eleNumber;

    private List<EleTz> tzList;

}
