package com.wim.aero.acs.protocol.accessLevel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title: ReaderTz
 * @author: Ellie
 * @date: 2022/03/31 09:07
 * @description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderTz {
    private int readerId;
    private int tz;
}
