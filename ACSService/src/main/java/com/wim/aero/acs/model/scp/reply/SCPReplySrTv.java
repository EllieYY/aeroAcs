package com.wim.aero.acs.model.scp.reply;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @title: SCPReplySrTv
 * @author: Ellie
 * @date: 2022/04/13 15:01
 * @description:
 **/
@Data
@Slf4j
public class SCPReplySrTv extends ReplyBody {
    private int first;				// number of the first Trigger Variable
    private int count;				// number of TV status entries
    private List<Integer> status;	// 100 - TV status: set/clear

    @Override
    public void process(int scpId) {
        // TODO:待处理
        log.info(this.toString());

    }
}
