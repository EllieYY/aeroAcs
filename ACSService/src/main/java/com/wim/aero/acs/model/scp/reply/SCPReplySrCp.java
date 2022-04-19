package com.wim.aero.acs.model.scp.reply;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @title: SCPReplySrCp
 * @author: Ellie
 * @date: 2022/04/13 11:34
 * @description:
 **/
@Data
@Slf4j
public class SCPReplySrCp extends ReplyBody {
    private int first;				// number of the first Control Point
    private int count;				// number of CP status entries
    private List<Integer> status;			// CP status (trl07 encoded)

    @Override
    public void process(int scpId) {
        // TODO：逻辑编号未必是连续的
        String info = "FirstCp:" + first + ", ";
        for (int i = 0;i < count; i++) {
            info += (first + i) + ":" + status.get(i);
        }
        log.info(info);
    }
}
