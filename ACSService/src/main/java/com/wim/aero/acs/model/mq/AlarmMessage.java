package com.wim.aero.acs.model.mq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date eventsTime;
    private int controllerId;
    private int eventSourceType;
    private int sourceCode;
    private int eventType;
    private int eventTypeCode;
    private String fullMemo;
}
