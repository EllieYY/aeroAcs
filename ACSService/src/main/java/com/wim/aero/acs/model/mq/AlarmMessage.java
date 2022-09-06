package com.wim.aero.acs.model.mq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @title: AlarmMessage
 * @author: Ellie
 * @date: 2022/04/14 10:58
 * @description:
 **/
@Data
@AllArgsConstructor
public class AlarmMessage {
    private long eventIndex;

    private long eventsTime;    // 事件时间戳
    private int controllerId;   // 控制器id
    private int eventSourceType;   // 来源类型
    private int sourceCode;     // 来源编号
    private int eventType;      // 事件类型
    private int eventTypeCode;  // 事件编号
    private int sourceTypeSerNo;    // 转换之后的来源
    private Integer eventDetailCode;   // 报警详情编码
    private String fullMemo;        // 原始事件信息

}
