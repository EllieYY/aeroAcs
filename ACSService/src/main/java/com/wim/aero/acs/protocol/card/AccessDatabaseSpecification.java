package com.wim.aero.acs.protocol.card;

import com.wim.aero.acs.message.Operation;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @title: AccessDatabaseSpecification
 * @author: Ellie
 * @date: 2022/03/14 10:27
 * @description: 硬件基本配置。10.3 Command 1105: Access Database Specification
 *
 *
 **/
@Data
public class AccessDatabaseSpecification extends Operation {
    private int lastModified = 0;
    private int nScpID;
    private int nCards; // 0 - 清除数据，不改变格式。Number of cardholder records to allocate (long)
    private int nAlvl; // Number of access levels per cardholder. Maximum value of 32.
    private int nPinDigits;

    // 附加码 0 = none, 1 = 8-bit (1byte), 2 = 32-bit (4 bytes)
    private int bIssueCode;
    private int bApbLocation;   // area based anti-passback. 0 or 1

    /**
     * 0 = Do not store
     * 1 = Store Date only
     * 2 = Store Date and Time
     */
    private int bActDate;
    private int bDeactDate;

    private int bVacationDate;  // 0 or 1
    private int bUpgradeDate;
    private int bUserLevel; // Valid values are 0-7.
    private int bUseLimit; // Valid values are 0 or 1.
    private int bSupportTimeApb; // Save the time, date and the ACR number of last entry flag. Valid values are 0 or 1.
    private int nTz; // Precision access: One access level with an array of time zone entries.
    private int bAssetGroup; // Store asset group code (2 bytes). Valid values are 0 or 1.
    private int nHostResponseTimeout; // Time to wait for HOST approved access response. default 5s
    private int nMxmTypeIndex = 0;
    private int nAlvlUse4Arq;
    private int nFreeformBlockSize = 0;

    private List<Integer> fieldInfo = Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);

    private int nEscortTimeout; // 陪同卡超时时间，default 15s. The valid range is 1 - 60 seconds.
    private int nMultiCardTimeout; // 多卡访问超时时间，default 15s. The valid range is 1 - 60 seconds.
    private int nAssetTimeout; // Set to 0 - N/A
    private int bAccExceptionList; // Indicates if access exception lists are being used
    private int adbFlags;   // Flags field for specifying additional flags
}
