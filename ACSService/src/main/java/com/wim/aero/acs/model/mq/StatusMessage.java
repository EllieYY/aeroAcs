package com.wim.aero.acs.model.mq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @title: StatusMessage
 * @author: Ellie
 * @date: 2022/04/19 19:30
 * @description:
 **/
@Data
@NoArgsConstructor
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
    private int sourceTypeSerNo;    // 转换之后的来源
    private String fullMemo;
}
