package com.wim.aero.acs.model.command;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @title: CommandDownloadInfo
 * @author: Ellie
 * @date: 2022/03/31 15:01
 * @description: 记录命令下载到控制器的情况
 **/
@Data
public class CmdDownloadInfo {
    private int scpId;
    private String msg;
    private String streamId;
    private long seqNo;
    private String reason;
    private int code;
    private String cardNo;

    public CmdDownloadInfo(int scpId, String msg, String streamId, long seqNo, int code) {
        this.scpId = scpId;
        this.msg = msg;
        this.streamId = streamId;
        this.seqNo = seqNo;
        this.code = code;
    }
}
