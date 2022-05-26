package com.wim.aero.acs.model.scp.transaction;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.model.mq.AccessMessage;
import com.wim.aero.acs.service.QueueProducer;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @title: TypeCardFull
 * @author: Ellie
 * @date: 2022/04/01 16:13
 * @description: TypeCardFull, TypeDblCardFull, TypeI64CardFull, TypeI64CardFullIc32 (5:x, 21:x, 37:x, 53:x)
 *
 * // transaction codes for tranTypeCardFull:
 * //		 1	- request rejected: access point "locked"
 * //		 2	- request accepted: access point "unlocked"
 * //		 3	- request rejected: invalid facility code
 * //		 4	- request rejected: invalid f/c extension
 * //		 5	- request rejected: not in card file
 * //		 6	- request rejected: invalid issue code
 * //		 7	- request granted: f/c verified, not used
 * //		 8	- request granted: f/c verified, door used
 * //		 9	- access denied - asked for host approval, then timed out
 * //		10	- reporting that this card is "about to get access granted" (expecting C_329 Host Response)
 * //		11  - access denied count exceeded
 * //		12	- access denied - asked for host approval, then host denied
 * //		13  - request rejected: Airlock is Busy
 * // 		16 - request granted: card granted access from cache, not in card file
 * // 		17 - request granted: card granted access from cache, invalid facility code
 * // 		18 - request granted: card granted access from cache, invalid issue code
 **/
@Data
public class TypeCardFull extends TransactionBody {
    private int format_number;		// index to the format table that was used, negative if reverse
    private long facility_code;		// facility code
    private long cardholder_id;		// cardholder ID number
    private int	issue_code;			// issue code
    private int	floor_number;		// zero if not available (or not supported), else 1==first floor, ...
    private String encoded_card;	// encoded_card[32];Large encoded ID. (up to 32 bytes reported)

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
