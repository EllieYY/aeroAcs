package com.wim.aero.acs.protocol.timezone;

import com.wim.aero.acs.db.entity.DHoliday;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;

/**
 * @title: Holiday
 * @author: Ellie
 * @date: 2022/03/10 14:29
 * @description: 9.3 Command 1104: Holiday Configuration
 * 日期重复添加不会覆盖，添加之前最好删除之前的holiday
 **/
@Data
public class Holiday extends Operation {
    @CmdProp(index = 2)
    private Integer lastModified = 0;   // default 0

    @CmdProp(index = 3)
    private Integer scpNumber;

    @CmdProp(index = 4)
    private Integer number = -1;

    /** 假期开始日期
     * year = 0: 删除所有holidays
     */
    @CmdProp(index = 5)
    private Integer year;

    @CmdProp(index = 6)
    private Integer month;

    @CmdProp(index = 7)
    private Integer day;

    /** 假期天数 - 最大值127
     * 不包含假期开始天数：实际持续天数为 extend+1
     */
    @CmdProp(index = 8)
    private Integer extend;

    /** 假期格式： A holiday can belong to multiple holiday types
     *  bit: | 7 | 6 | 5 | 4 | 3 | 2 | 1 | 0 |
     * type: | 7 | 6 | 5 | 4 | 3 | 2 | 1 | 0 |
     * 值为0: 删除日期被指定参数覆盖的所有的holiday
     */
    @CmdProp(index = 9)
    private Integer typeMask;

    public static Holiday fronDb(int scpId, DHoliday holiday) {
        Holiday result = new Holiday();
        result.setScpNumber(scpId);
        result.setExtend(holiday.getContinuedDay());

        // 日期获取
        Date date = holiday.getBeginDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        result.setYear(calendar.get(Calendar.YEAR));
        result.setMonth(calendar.get(Calendar.MONDAY));
        result.setDay(calendar.get(Calendar.DATE));

        result.setTypeMask(holiday.getHolidayType());

        return result;
    }


}
