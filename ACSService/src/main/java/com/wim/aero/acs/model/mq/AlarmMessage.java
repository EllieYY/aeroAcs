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
public class AlarmMessage extends StatusMessage {

    private int eventDetailCode;   // 报警详情编码

    public AlarmMessage(long eventIndex, long eventsTime, int controllerId,
                        int eventSourceType, int sourceCode, int eventType, int eventTypeCode,
                        int status, int sourceTypeSerNo, String fullMemo, int eventDetailCode) {
        super(eventIndex, eventsTime, controllerId,
                eventSourceType, sourceCode, eventType, eventTypeCode, status, sourceTypeSerNo, fullMemo);
        this.eventDetailCode = eventDetailCode;
    }
}
