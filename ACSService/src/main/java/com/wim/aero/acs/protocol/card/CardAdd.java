package com.wim.aero.acs.protocol.card;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.List;

/**
 * @title: CardRecord
 * @author: Ellie
 * @date: 2022/03/14 10:47
 * @description: 10.4 Command 8304: Access Database Card Records
 **/
@Data
public class CardAdd extends Operation {
    @CmdProp(index = 2)
    private int lastModified = 0;

    @CmdProp(index = 3)
    private int scpNumber; // SCP number

    @CmdProp(index = 4)
    private int flags;

    @CmdProp(index = 5)
    private int cardNumber;

    @CmdProp(index = 6)
    private int issueCode;

    @CmdProp(index = 7)
    private int pin;

    @CmdProp(index = 8)
    private List<Integer> alvl;   // 32

    @CmdProp(index = 40)
    private int apbLoc;
//    Anti-passback location: sets anti-passback location in the cardholder record unless the ADBC_
//    NOAPB flag is set.

    @CmdProp(index = 41)
    private int useCount;

    @CmdProp(index = 42)
    private int actTime;
//    Activate date/time. As specified in Command 1105: Access Database Specification, specify date
//    or date/time. Old forms of this command specify the number of elapsed days from January 1,
//            1900. The new form of the command specifies the number of elapsed seconds from January 1,
//            1970. Both are in SCP local time. This field is a long. See remarks for more details.

    @CmdProp(index = 43)
    private int dactTime;
//    Deactivation date/time. As specified in Command 1105: Access Database Specification, specify
//    date or date/time. Old forms of this command specify the number of elapsed days from January 1,
//            1900. The new form of the command specifies the number of elapsed seconds from January 1,
//            1970. Both are in SCP local time. This field is a long. See remarks for more details.

    @CmdProp(index = 44)
    private int vacDate;
//Starting date in days from January 1, 1900. During the vacation period the card will be treated as
//    deactivated and will result in transaction 6:1 “Request rejected: deactivated card”.

    @CmdProp(index = 45)
    private int vacDays; // The number of vacation days (in addition to the first one).

    @CmdProp(index = 46)
    private int tmpDate;
//    Temporary upgrade starting date in days from January 1, 1900. Uses the access level stored in the
//    last access level index for the card. Requires nAlvl in Command 1105: Access Database
//    Specification to be greater than 1. During temporary upgrade period the access level at this index
//    will be examined, otherwise the access level at this index location will not be used for the card.

    @CmdProp(index = 47)
    private int tmpDays;

    @CmdProp(index = 48)
    private List<Integer> userLevel;    // 8
//    The user level, for command entry. See Command 1141: Extended User Command Configuration.
//    User levels are not used for access rights, but used in triggers. Each value is a byte.

    @CmdProp(index = 56)
    List<Integer> alvlPrec;   // 64. Precision access: levels up to 64 ACRs. Unused levels are marked as “0”.

    @CmdProp(index = 120)
    private int assetGroup = 0;
}
