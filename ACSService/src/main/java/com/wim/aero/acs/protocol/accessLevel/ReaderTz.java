package com.wim.aero.acs.protocol.accessLevel;

import lombok.Data;

/**
 * @title: ReaderTz
 * @author: Ellie
 * @date: 2022/03/31 09:07
 * @description:
 **/
@Data
public class ReaderTz {
    private int readerId;
    private int tz;
}
