package com.wim.aero.acs.model.command;

import lombok.Data;

import java.util.Date;

/**
 * @title: MultiCmdResponse
 * @author: Ellie
 * @date: 2022/06/09 11:34
 * @description:
 **/
@Data
public class MultiCmdResponse {
    private String date;
    private int cmdCount;
}
