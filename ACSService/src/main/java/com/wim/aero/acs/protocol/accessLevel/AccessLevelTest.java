package com.wim.aero.acs.protocol.accessLevel;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.model.db.AccessLevelInfo;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * @title: AccessLevelSpecification
 * @author: Ellie
 * @date: 2022/03/14 14:24
 * @description: 10.6 Command 124:Access Level Specification, Expanded Tests
 **/
@Data
public class AccessLevelTest extends Operation {
    @CmdProp(index = 2)
    private Integer nScpNumber;

    @CmdProp(index = 3)
    private Integer nAlvlnumber; // Access level number

    @CmdProp(index = 4)
    private Integer nActYear; // java.time.Year to activate access level.

    @CmdProp(index = 5)
    private Integer nActMonth; // java.time.Month of the year to activate access level; 1-12

    @CmdProp(index = 6)
    private Integer nActDay; // Day of month to activate access level; 1-31

    @CmdProp(index = 7)
    private Integer nActHh; // Hour of day to activate access level; 0-23

    @CmdProp(index = 8)
    private Integer nActMn; // Minute of the hour to activate access level; 0-59

    @CmdProp(index = 9)
    private Integer nActSs; // Second of minute to activate access level; 0-59

    @CmdProp(index = 10)
    private Integer nDactYear; // Year to deactivate access level.

    @CmdProp(index = 11)
    private Integer nDactMonth; // Month of the year to deactivate access level; 1-12

    @CmdProp(index = 12)
    private Integer nDactDay; // Day of month to deactivate access level; 1-31

    @CmdProp(index = 13)
    private Integer nDactHh; // Hour of day to deactivate access level; 0-23

    @CmdProp(index = 14)
    private Integer nDactMn; // Minute of the hour to deactivate access level; 0-59

    @CmdProp(index = 15)
    private Integer nDactSs; // Second of minute to deactivate access level; 0-59

    /**
     * 0 = Not an escort
     * 1 = Is an escort
     * 2 = Escort required
     */
    @CmdProp(index = 16)
    private Integer nEscortCode; // Escort code of user at reader where the time zone is active:

    @CmdProp(index = 17)
    private Integer operMode = 1;

    public static AccessLevelTest fromDb(AccessLevelInfo info) {
        AccessLevelTest result = new AccessLevelTest();

        result.setNScpNumber(info.getNScpNumber());
        result.setNAlvlnumber(info.getNAlvlnumber());


        if (info.getActiveDate() != null && info.getDeactiveDate() != null) {
            Calendar calendar = Calendar.getInstance();
            Date activeDate = info.getActiveDate();
            calendar.setTime(activeDate);
            result.setNActYear(calendar.get(Calendar.YEAR));
            result.setNActMonth(calendar.get(Calendar.MONTH) + 1);
            result.setNActDay(calendar.get(Calendar.DATE));
            result.setNActHh(calendar.get(Calendar.HOUR_OF_DAY));
            result.setNActMn(calendar.get(Calendar.MINUTE));
            result.setNActSs(calendar.get(Calendar.SECOND));

            Date deactiveDate = info.getDeactiveDate();
            calendar.setTime(deactiveDate);
            result.setNDactYear(calendar.get(Calendar.YEAR));
            result.setNDactMonth(calendar.get(Calendar.MONTH) + 1);
            result.setNDactDay(calendar.get(Calendar.DATE));
            result.setNDactHh(calendar.get(Calendar.HOUR_OF_DAY));
            result.setNDactMn(calendar.get(Calendar.MINUTE));
            result.setNDactSs(calendar.get(Calendar.SECOND));
        }

        if (StringUtils.hasText(info.getNEscortCode())) {
            result.setNEscortCode(Integer.parseInt(info.getNEscortCode()));
        }

        return result;
    }
}
