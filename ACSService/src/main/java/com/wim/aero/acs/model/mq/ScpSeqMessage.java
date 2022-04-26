package com.wim.aero.acs.model.mq;

import lombok.Data;

import java.util.Date;

/**
 * @title: ScpReq
 * @author: Ellie
 * @date: 2022/04/26 15:23
 * @description:
 **/
@Data
public class ScpSeqMessage {
    private int scpId;
    private long seq;
    private int status;
    private int reason;
    private Date cmdDate;

    public ScpSeqMessage(int scpId, long seq, int status, int reason) {
        this.scpId = scpId;
        this.seq = seq;
        this.status = status;
        this.reason = reason;
        cmdDate = new Date();
    }
}