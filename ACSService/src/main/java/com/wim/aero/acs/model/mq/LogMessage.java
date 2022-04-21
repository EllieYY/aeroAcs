package com.wim.aero.acs.model.mq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @title: LogMessage
 * @author: Ellie
 * @date: 2022/04/13 11:15
 * @description:
 **/
@Data
@AllArgsConstructor
public class LogMessage {
    private long eventIndex;

    private long eventsTime;
    private int controllerId;
    private int eventSourceType;
    private int sourceCode;
    private int eventType;
    private int eventTypeCode;
    private String fullMemo;


}
