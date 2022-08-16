package com.wim.aero.acs.protocol.device.reader;

import com.alibaba.druid.sql.visitor.functions.If;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;
import org.apache.activemq.command.Command;

import javax.validation.Valid;
import java.awt.*;
import java.io.Reader;

/**
 * @title: ReaderLED
 * @author: Ellie
 * @date: 2022/08/16 09:42
 * @description: 8.4 Command 122: Reader LED/Buzzer Function Specs
 **/
@Data
public class ReaderLED extends Operation {
    @CmdProp(index = 2)
    private Integer lastModified = 0;

    @CmdProp(index = 3)
    private Integer scpNumber;

    @CmdProp(index = 4, defaultValue = "1")
    private Integer ledMode = 1;  // LED mode. Selects LED mode table. Valid values are 1, 2, and 3.

    @CmdProp(index = 5, defaultValue = "1")
    private Integer rledId = 12;

    /**
        //0 = Off
        //1 = Red
        //2 = Green
        //3 = Amber
        //4 = Blue (Available only on select OSDP readers)
        //5 = Magenta (Available only on select OSDP readers)
        //6 = Cyan (Available only on select OSDP readers)
        //7 = White (Available only on select OSDP readers)
     */
    @CmdProp(index = 6, defaultValue = "2")
    private Integer on_color = 2;   // Color to display during on_time

    @CmdProp(index = 7, defaultValue = "1")
    private Integer off_color = 1;  // Color to display during off_time

    @CmdProp(index = 8, defaultValue = "50")
    private Integer on_time = 50;    // Duration to display on_color (0.1s/tick)

    @CmdProp(index = 9)
    private Integer off_time = 0;   //Duration to display off_color (0.1s/tick)

    @CmdProp(index = 10, defaultValue = "1")
    private Integer repeat_count = 1; //If transient, the number of times to repeat cycle. Valid values are 1 to 255.

    @CmdProp(index = 11, defaultValue = "1")
    private Integer beep_count = 1;

    @CmdProp(index = 12)
    private Integer rdiLine1; // Text index of LCD text specified in Command 123: Reader LCD Text Configuration for Line1 (top). Zero to stay with current.
    @CmdProp(index = 13)
    private Integer rdiLine2;

    public static ReaderLED defualtSetting(int scpId) {
        ReaderLED result = new ReaderLED();
        result.setScpNumber(scpId);

        return result;
    }
}
