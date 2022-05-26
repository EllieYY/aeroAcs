package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.AccessMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;

import java.util.Date;

/**
 * @title: TypeCardID
 * @author: Ellie
 * @date: 2022/04/01 16:11
 * @description: TypeCardID, TypeDblCardID, TypeI64CardID (6:x, 22:x, 38:x)
 *
 * // transaction codes for tranTypeCardID:
 * //		 1 - request rejected: de-activated card
 * //		 2 - request rejected: before activation date
 * //		 3 - request rejected: after expiration date
 * //		 4 - request rejected: invalid time
 * //		 5 - request rejected: invalid PIN
 * //		 6 - request rejected: anti-passback violation
 * //		 7 - request granted:  apb violation, not used
 * //		 8 - request granted:  apb violation, used
 * //		 9 - request rejected: duress code detected
 * //		10 - request granted:  duress, used
 * //		11 - request granted:  duress, not used
 * //		12 - request granted:  full test, not used
 * //		13 - request granted:  full test, used
 * //		14 - request denied:   never allowed at this reader (all Tz's == 0)
 * //		15 - request denied:   no second card presented
 * //		16 - request denied:   occupancy limit reached
 * //		17 - request denied:   the area is NOT enabled
 * //		18 - request denied:   use limit
 * //		19 - request denied:   unauthorized assets
 * //		20 - request denied:   biometric verification error
 * //		21 - granting access:  used/not-used transaction will follow
 * //      22 - request rejected: failed the bio test: no bio record
 * //      23 - request rejected: failed the bio test: no bio device
 * //      24 - request rejected: no escort card presented
 * //      25 - request rejected: obsolete >> m1m/m2m last special user may not exit
 * //      26 - request rejected: obsolete >> m1m/m2m area has no special users yet
 * //      27 - request rejected: obsolete >> m1m/m2m supervisor approval timeout
 * //      28 - request rejected: not used
 * //		29 - request rejected: Airlock is Busy
 * //		30 - request rejected: Incomplete CARD & PIN sequence
 * //		31 - request granted: Double-card event.
 * //		32 - request granted: Double-card event while in uncontrolled state (locked/unlocked).
 * //		33 - request rejected: Elevators - floor not in floors served
 * //		34 - request rejected: Elevators - floor request not authorized
 * //		35 - request rejected: Elevators - timeout
 * //		36 - request rejected: Elevators - unknown error
 * //		37 - request granted: local verification: access grant, not used
 * //		38 - request granted: local verification: access grant, used
 * //		39 - granting access: requires escort, pending escort card
 * //      40 - request denied: violates minimum occupancy count
 * //      41 - request denied: card pending at another reader
 * // 		42 - request granted: known card granted access from cache
 * // 		43 - request denied: known card denied access from cache
 **/
@Data
public class TypeCardID extends TransactionBody {
    private int	format_number;			// index to the format table that was used, negative if reverse read
    private long cardholder_id;			// cardholder ID number
    private int	floor_number;			// zero if not available (or not supported), else 1==first floor, ...
    private int	card_type_flags;		// Card type flags (bit-0 = escort, bit-1 = escort required)
    private int	elev_cab;				// Elevator cab number

    @Override
    public void process(QueueProducer queueProducer, SCPReplyTransaction transaction) {
        String cardHolder = String.valueOf(cardholder_id);

        int scpId = transaction.getScpId();
        long date = transaction.getTime() * 1000;
        long index = transaction.getSerNum();
        int sourceType = transaction.getSourceType();
        int sourceNum = transaction.getSourceNumber();
        int tranType = transaction.getTranType();
        int tranCode = transaction.getTranCode();

        queueProducer.sendAccessMessage(
                new AccessMessage(index, date, scpId, sourceType, sourceNum, tranType, tranCode, cardHolder,
                        Constants.TRAN_TABLE_SRC_ACR, this.toString())
        );
    }
}
