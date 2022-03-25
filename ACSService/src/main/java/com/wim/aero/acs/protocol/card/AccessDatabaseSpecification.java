package com.wim.aero.acs.protocol.card;

import com.wim.aero.acs.db.entity.DevControllerCommonAttribute;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.*;

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
    @CmdProp(index = 2)
    private Integer lastModified;

    @CmdProp(index = 3)
    private Integer nScpID;

    @CmdProp(index = 4)
    private Integer nCards; // 0 - 清除数据，不改变格式。Number of cardholder records to allocate (long)

    @CmdProp(index = 5)
    private Integer nAlvl; // Number of access levels per cardholder. Maximum value of 32.

    @CmdProp(index = 6)
    private Integer nPinDigits;

    // 附加码 0 = none, 1 = 8-bit (1byte), 2 = 32-bit (4 bytes)
    @CmdProp(index = 7)
    private Integer bIssueCode;

    @CmdProp(index = 8)
    private String bApbLocation;   // area based anti-passback. 0 or 1

    /**
     * 0 = Do not store
     * 1 = Store Date only
     * 2 = Store Date and Time
     */
    @CmdProp(index = 9)
    private String bActDate;
    @CmdProp(index = 10)
    private String bDeactDate;

    @CmdProp(index = 11)
    private String bVacationDate;  // 0 or 1
    @CmdProp(index = 12)
    private String bUpgradeDate;
    @CmdProp(index = 13)
    private String bUserLevel; // Valid values are 0-7.
    @CmdProp(index = 14)
    private String bUseLimit; // Valid values are 0 or 1.
    @CmdProp(index = 15)
    private String bSupportTimeApb; // Save the time, date and the ACR number of last entry flag. Valid values are 0 or 1.
    @CmdProp(index = 16)
    private Integer nTz; // Precision access: One access level with an array of time zone entries.
    @CmdProp(index = 17)
    private Integer bAssetGroup = 0; // Store asset group code (2 bytes). Valid values are 0 or 1.
    @CmdProp(index = 18)
    private Integer nHostResponseTimeout; // Time to wait for HOST approved access response. default 5s
    @CmdProp(index = 19)
    private Integer nMxmTypeIndex = 0;
    @CmdProp(index = 20)
    private Integer nAlvlUse4Arq = 0;
    @CmdProp(index = 21)
    private Integer nFreeformBlockSize = 0;

    @CmdProp(index = 22, enCodec = "formatList")
    private List<Integer> fieldInfo = new ArrayList<>(Collections.nCopies(16, 0));

    @CmdProp(index = 38)
    private Integer nEscortTimeout; // 陪同卡超时时间，default 15s. The valid range is 1 - 60 seconds.
    @CmdProp(index = 39)
    private Integer nMultiCardTimeout; // 多卡访问超时时间，default 15s. The valid range is 1 - 60 seconds.
    @CmdProp(index = 40)
    private Integer nAssetTimeout = 0; // Set to 0 - N/A
    @CmdProp(index = 41)
    private Integer bAccExceptionList = 0; // Indicates if access exception lists are being used
    @CmdProp(index = 42)
    private Integer adbFlags = 0;   // Flags field for specifying additional flags

    public static AccessDatabaseSpecification fromDb(DevControllerCommonAttribute detail) {
        AccessDatabaseSpecification result = new AccessDatabaseSpecification();
        result.setNCards(detail.getMaxCardNum());
        result.setNAlvl(detail.getAccessLevelNum());

        // nPinDigits生成逻辑
        if (StringUtils.hasText(detail.getForceAlarmMode()) &&
                Optional.ofNullable(detail.getForceAlarmMode()).isPresent() &&
                Optional.ofNullable(detail.getMaxPin()).isPresent() &&
                Optional.ofNullable(detail.getMaxCardLength()).isPresent()) {
            int duressMode = Integer.parseInt(detail.getForceAlarmMode());
            int duressOffset = detail.getForceAlarmOffset();
            int cardIdSize = detail.getMaxCardLength();
            int nPinDigit = detail.getMaxPin();

            int nPinDigits = 4096 * duressMode + 256 * duressOffset + 16 * cardIdSize + nPinDigit;

            result.setNPinDigits(nPinDigits);
        }

        result.setBIssueCode(detail.getAdditionalFlag());
        result.setBApbLocation(detail.getApbControlFlag());
        result.setBActDate(detail.getCardSaveFlag());
        result.setBDeactDate(detail.getCardInvalidFlag());
        result.setBVacationDate(detail.getSaveVacationFlag());
        result.setBUpgradeDate(detail.getSaveTempFlag());
//        result.setBUserLevel(detail.get);
        result.setBUseLimit(detail.getEnableTimesFlag());
        result.setBSupportTimeApb(detail.getApbTimeFlag());
        result.setNHostResponseTimeout(detail.getHostOverTime());
        result.setNEscortTimeout(detail.getSecCardOverTime());
        result.setNMultiCardTimeout(detail.getMorCardOverTime());

        // 附加权限设置 -- 暂不启用，如果设置，默认按最大读卡器个数来
        if (StringUtils.hasText(detail.getAdditionalAccessFlag())){
            result.setNTz(64);
        }

        return result;
    }
}
