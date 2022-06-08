package com.wim.aero.acs.model.mq;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @title: ScpReq
 * @author: Ellie
 * @date: 2022/04/26 15:23
 * @description:
 **/
@Data
@NoArgsConstructor
public class ScpSeqMessage {
    private int scpId;
    private long seq;
    private int status;
    private int reason;
    private Date cmdDate;
    private String detail;

    public ScpSeqMessage(int scpId, long seq, int status, int reason, String detail) {
        this.scpId = scpId;
        this.seq = seq;
        this.status = status;
        this.reason = reason;
        cmdDate = new Date();
        this.detail = detail;
    }
}
