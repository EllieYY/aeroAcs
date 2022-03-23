package com.wim.aero.acs.protocol.timezone;

import lombok.Data;

/**
 * @title: TimeInterval
 * @author: Ellie
 * @date: 2022/03/10 15:11
 * @description:
 **/
@Data
public class TimeInterval {
    /**
     *         bit: | 15 | 14 | 13 | 12 | 11 | 10 | 9 | 8 |  7  |  6  | 5 |  4 | 3 | 2 | 1 |  0  |
     *        type: |  7 |  6 |  5 |  4 |  3 |  2 | 1 | 0 |
     * day of week:                                          0  | sat | F | Th | W | T | M | Sun |
     */
    private int dayMask;

    /**
     * Start time in minutes from 12:00 am
     */
    private int start;

    /**
     * End time in minutes from 12:00 am
     */
    private int end;
}
