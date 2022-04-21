package com.wim.aero.acs.model.mq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @title: StatusMessage
 * @author: Ellie
 * @date: 2022/04/19 19:30
 * @description:
 **/
@Data
@AllArgsConstructor
public class StatusMessage {
    private long eventIndex;

    private long eventsTime;
    private int controllerId;
    private int eventSourceType;
    private int sourceCode;
    private int eventType;
    private int eventTypeCode;
    private int status;
    private String fullMemo;
}
