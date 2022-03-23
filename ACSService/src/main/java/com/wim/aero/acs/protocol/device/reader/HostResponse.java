package com.wim.aero.acs.protocol.device.reader;

import lombok.Data;

/**
 * @title: HostResponse
 * @author: Ellie
 * @date: 2022/03/14 10:10
 * @description: 远程开门。8.16 Command 329: Send Host Response
 **/
@Data
public class HostResponse {
    private int scp_number;
    private int acr_number;
    private int command; // 0 - deny access. 1 - allow access.
    private int cardholderId;
}
