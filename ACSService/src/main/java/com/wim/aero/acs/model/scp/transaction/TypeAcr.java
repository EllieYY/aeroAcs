package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;

import java.util.Date;

/**
 * @title: TypeAcr
 * @author: Ellie
 * @date: 2022/04/01 15:45
 * @description:
 *
 * transaction codes for tranTypeAcr: (same as ACR mode set command codes)
 * 1 - disabled
 * 2 - unlocked
 * 3 - locked (exit request enabled)
 * 4 - facility code only
 * 5 - card only
 * 6 - PIN only
 * 7 - card and PIN
 * 8 - PIN or card
 **/
@Data
public class TypeAcr extends TransactionBody {
    private int actl_flags;			// image of CC_ACR::actl_flags
    private int prior_flags;		// flags prior to mode set
    private int prior_mode;			// mode prior to mode set
    private int actl_flags_e;		// image of CC_ACR::spare flags
    private int prior_flags_e;		// prior image of CC_ACR::spare flags
    private long auth_mod_flags;		// Not used
    private long prior_auth_mod_flags; // Not used

    @Override
    public void process(QueueProducer queueProducer, SCPReplyTransaction transaction) {

    }
}
