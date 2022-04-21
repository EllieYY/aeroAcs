package com.wim.aero.acs.model.mq;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @title: AccessMessage
 * @author: Ellie
 * @date: 2022/04/13 11:13
 * @description:
 **/
@Data
@AllArgsConstructor
public class AccessMessage {
    private long eventIndex;

    private long eventsTime;

    private int controllerId;
    private int eventSourceType;
    private int sourceCode;
    private int eventType;
    private int eventTypeCode;
    private String cardNo;
    private String fullMemo;
}
