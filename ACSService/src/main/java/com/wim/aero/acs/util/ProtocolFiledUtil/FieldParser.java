package com.wim.aero.acs.util.ProtocolFiledUtil;

import com.wim.aero.acs.protocol.accessLevel.ReaderTz;
import com.wim.aero.acs.protocol.device.mp.MpType;
import com.wim.aero.acs.protocol.timezone.TimeInterval;
import lombok.val;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @title: FieldParser
 * @author: Ellie
 * @date: 2022/02/16 15:44
 * @description: 字段解析工具
 **/
public class FieldParser {
    public static String intToStr(Integer val) {
        return val == null ? "" : String.valueOf(val);
    }

    public static Integer strToInt(String val) {
        return StringUtils.hasText(val) ? null : Integer.valueOf(val);
    }

    public static String formatStr(String val) {
        return "\"" + val + "\"";
    }

    public static String formatList(List<Integer> val) {
        return String.join(" ", val.stream().map(String::valueOf).collect(Collectors.toList()));
    }

    public static String formatTimeInterval(List<TimeInterval> valList) {
        return String.join(" ", valList.stream().map(TimeInterval::toString).collect(Collectors.toList()));
    }

    public static String formatMpList(List<MpType> valList) {
        return String.join(" ", valList.stream().map(MpType::toString).collect(Collectors.toList()));
    }

    //
    public static String formatReaderTz(List<ReaderTz> valList) {
        return String.join(" ", valList.stream().map(ReaderTz::toString).collect(Collectors.toList()));
    }

}
