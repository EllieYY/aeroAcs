package com.wim.aero.acs.model.command;

import lombok.Data;

import java.util.Date;

/**
 * @title: CommandInfp
 * @author: Ellie
 * @date: 2022/04/18 09:08
 * @description:
 **/
@Data
public class CommandInfo {
    private String streamId;
    private long seqId;
    private String Command;
    private long taskId;
    private String scpId;
    private int commandStatus;    // 命令状态：SCPReplyComnd错误码
    private String reason;        // SCPReplyComnd返回内容
    private Date cmdDate;         // 命令时间
    private int commCode;         // 控制器通信服务返回的错误码

    public CommandInfo(long taskId, String streamId, String scpId, String command, int commandStatus) {
        this.streamId = streamId;
        Command = command;
        this.taskId = taskId;
        this.scpId = scpId;
        this.commandStatus = commandStatus;
        this.cmdDate = new Date();
    }
}
