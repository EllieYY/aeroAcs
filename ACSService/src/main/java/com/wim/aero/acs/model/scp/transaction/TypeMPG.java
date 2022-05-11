package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * @title: TypeMPG
 * @author: Ellie
 * @date: 2022/04/01 15:42
 * @description:
 *
 * transaction codes for tranTypeMpg
 * 1 - first disarm command executed (mask_count was 0, all MPs got masked)
 * 2 - subsequent disarm command executed (mask_count incremented, MPs already masked)
 * 3 - override command: armed (mask_count cleard, all points unmasked)
 * 4 - override command: disarmed (mask_count set, unmasked all points)
 * 5 - force arm command, MPG armed, (may have active zones, mask_count is now zero)
 * 6 - force arm command, MPG not armed (mask_count decremented)
 * 7 - standard arm command, MPG armed (did not have active zones, mask_count is now zero)
 * 8 - standard arm command, MPG did not arm, (had active zones, mask_count unchanged)
 * 9 - standard arm command, MPG still armed, (mask_count decremented)
 * 10 - override arm command, MPG armed (mask_count is now zero)
 * 11 - override arm command, MPG did not arm, (mask_count decremented)
 **/
@Data
@Slf4j
public class TypeMPG extends TransactionBody {
    private int mask_count;			// current mask count of this MPG
    private int nActiveMps;			// number of active Monitor Points
    List<Integer> nMpList;		    // IntVB nMpList[10*2]; list of the first 10 active Point Pairs: "Type-Number"

    @Override
    public void process(QueueProducer queueProducer, SCPReplyTransaction transaction) {

    }
}
