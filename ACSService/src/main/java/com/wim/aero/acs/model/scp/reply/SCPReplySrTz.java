package com.wim.aero.acs.model.scp.reply;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @title: SCPReplySrTz
 * @author: Ellie
 * @date: 2022/04/13 15:00
 * @description:
 **/
@Data
@Slf4j
public class SCPReplySrTz extends ReplyBody {
    private int first;				// number of the first Timezone
    private int count;				// number of TZ status entries
    private List<Integer> status;			// 100 - TZ status is bit-mapped:
                                    // 0x01 mask == tz active
                                    // 0x02 mask == time based scan state
                                    // 0x04 mask == time scan override

    @Override
    public void process(int scpId) {

        // TODO:待处理
        log.info(this.toString());
    }
}
