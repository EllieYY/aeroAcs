package com.wim.aero.acs.protocol.timezone;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.List;

/**
 * @title: TimeZone
 * @author: Ellie
 * @date: 2022/03/10 14:05
 * @description: 9.2 Command 3103: Extended Time Zone Act Specification
 **/
@Data
public class TimeZone extends Operation {
    @CmdProp(index = 2)
    private int lastModified = 0;

    @CmdProp(index = 3)
    private int nScpId;

    /** 时间区编号 1 - 255
     * 0 不可用
     */
    @CmdProp(index = 4)
    private int number;

    /**
     * 0 - off:inactive
     * 1 - on:active
     * 2 - Scan:优先判断holiday
     * 3 - OneTimeEvent：Day Mask符合expTest字段的日期
     * 4 - [ Holiday || Day Mask ]
     * 5 - [ Holiday && Day Mask ]
     */
    @CmdProp(index = 5)
    private int mode = 1;

    /**
     *  seconds from January 1, 1970 in local time
     */
    @CmdProp(index = 6)
    private long actTime = 0;

    @CmdProp(index = 7)
    private long deactTime = 0;

    @CmdProp(index = 8)
    private int intervalsNum; // 0~12

    @CmdProp(index = 9, enCodec = "formatTimeInterval")
    private List<TimeInterval> intervals;

    @CmdProp(index = 12, enCodec = "formatStr")
    private String expTest = "";


    public void updateIntervalSize() {
        intervalsNum = intervals.size();
    }

}
