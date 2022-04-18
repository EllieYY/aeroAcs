package com.wim.aero.acs.protocol;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;

/**
 * @title: DaylightSavingTime
 * @author: Ellie
 * @date: 2022/04/15 14:50
 * @description: 夏令时配置 11164.5 Command 1116: Daylight Saving Time Configuration
 **/
@Data
public class DaylightSavingTimeConfiguration extends Operation {
    @CmdProp(index = 2)
    private int nScpID; // SCP Id

    @CmdProp(index = 3)
    private int nSYear; // Start year, e.g. 1994

    @CmdProp(index = 4)
    private int nSMonth; // Start month, 1-12

    @CmdProp(index = 5)
    private int nSDay;  // Start day-of-month, 1-31

    @CmdProp(index = 6)
    private int nSHh;  // Start hours, 0-23 (specified in local standard time)

    @CmdProp(index = 7)
    private int nSMm;  // Start minutes, 0-59

    @CmdProp(index = 8)
    private int nSSs;  // Start seconds, 0-59

    @CmdProp(index = 9)
    private int nEYear; // End year, e.g. 1994

    @CmdProp(index = 10)
    private int nEMonth; // End month, 1-12

    @CmdProp(index = 11)
    private int nEDay; // End day-of-month, 1-31

    @CmdProp(index = 12)
    private int nEHh; // End hours 0-23 (specified in local standard time)

    @CmdProp(index = 13)
    private int nEMm; // End minutes 0-59

    @CmdProp(index = 14)
    private int nESs; // End seconds 0-59

    public DaylightSavingTimeConfiguration(int nScpID, Date start, Date end) {
        this.nScpID = nScpID;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        setNSYear(calendar.get(Calendar.YEAR));
        setNSMonth(1 + calendar.get(Calendar.MONTH));
        setNSDay(calendar.get(Calendar.DATE));
        setNSHh(calendar.get(Calendar.HOUR_OF_DAY));
        setNSMm(calendar.get(Calendar.MINUTE));
        setNSSs(calendar.get(Calendar.SECOND));

        calendar.setTime(end);
        setNEYear(calendar.get(Calendar.YEAR));
        setNEMonth(1 + calendar.get(Calendar.MONTH));
        setNEDay(calendar.get(Calendar.DATE));
        setNEHh(calendar.get(Calendar.HOUR_OF_DAY));
        setNEMm(calendar.get(Calendar.MINUTE));
        setNESs(calendar.get(Calendar.SECOND));
    }
}
