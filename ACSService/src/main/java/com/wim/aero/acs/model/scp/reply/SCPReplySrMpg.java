package com.wim.aero.acs.model.scp.reply;

import lombok.Data;

import java.util.List;

/**
 * @title: SCPReplySrMpg
 * @author: Ellie
 * @date: 2022/04/13 11:34
 * @description:
 **/
@Data
public class SCPReplySrMpg extends ReplyBody {
    private int number;							// MPG number
    private int mask_count;						// mask count
    private int num_active;						// number of active MPs
    private List<Integer> active_mp_list;	// list of the active point pairs (Type-Num) (MAX_MP_PER_MPG)

    @Override
    public void process() {

    }
}
