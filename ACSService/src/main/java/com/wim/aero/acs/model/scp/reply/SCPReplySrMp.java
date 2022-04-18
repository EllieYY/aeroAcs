package com.wim.aero.acs.model.scp.reply;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @title: SCPReplySrMp
 * @author: Ellie
 * @date: 2022/04/13 11:33
 * @description:
 **/
@Data
@Slf4j
public class SCPReplySrMp extends ReplyBody {
    private int first;				// number of the first Monitor Point
    private int count;				// number of MP status entries
    private List<Integer> status;			// MP status (trl07 encoded)

    @Override
    public void process(int scpId) {
        String info = "FirstCp:" + first + ", ";
        for (int i = 0;i < count; i++) {
            info += (first + i) + ":" + status.get(i);
        }
        log.info(info);
    }
}
