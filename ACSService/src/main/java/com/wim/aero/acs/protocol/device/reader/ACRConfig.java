package com.wim.aero.acs.protocol.device.reader;

import com.wim.aero.acs.db.entity.DevReaderDetail;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.List;

/**
 * @title: ACRConfig
 * @author: Ellie
 * @date: 2022/03/15 10:40
 * @description: 8.2 Command 115: Access Control Reader Configuration
 *
 * accessCfg读卡器类型
 * ------------------------------------------------------------------------
 * | ACR_A_SINGLE    | 0 | Single reader, controlling the door
 * | ACR_A_MASTER    | 1 | Paired readers, Master - this reader controls the door
 * | ACR_A_SLAVE     | 2 | Paired readers, Slave - this reader does not control door
 * | ACR_A_TURNSTILE | 3 | Turnstile Reader.
 * | ACR_A_EL1       | 4 | Elevator, no floor select feedback *
 * | ACR_A_EL2       | 5 | Elevator with floor select feedback *
 * ------------------+---+----------------------------------------------
 * Turnstile Reader. Two modes selected by:
 *  *      strike_t_min != strike_t_max (original implementation - an access grant pulses the strike output for 1 second)
 *  *      strike_t_min == strike_t_max (pulses the strike output after a door open/close signal for each additional access grant if several grants are waiting)
 *  *
 *
 * strikeMode 门磁上电
 * --------------------------------------------------------------------------
 * | ACR_S_NONE     | 0  | Do not use! This would allow the strike to stay active for the entire strike time allowing the door to be opened multiple times.
 * | ACR_S_OPEN     | 1  | Deactivate strike when door opens.
 * | ACR_S_CLOSE    | 2  | Deactivate strike on door close or strike_t_max expires.
 * | ACR_S_TAILGATE | 16 | Used with ACR_S_OPEN or ACR_S_CLOSE
 * |----------------+----+-------------------------------------------------
 *
 * altrdrSpec 备用读卡器
 * ----------------------------------------------------------------------
 * | ACR_AR_NONE | 0 | Ignore data from alternate reader
 * | ACR_AR_NRML | 1 | Normal Access Reader (two read heads to the same processor)
 * ----------------------------------------------------------------------
 *
 *
 **/
@Data
public class ACRConfig extends Operation {
    @CmdProp(index = 2)
    private int lastModified = 0;

    @CmdProp(index = 3)
    private int scpNumber;

    @CmdProp(index = 4)
    private int acrNumber;

    @CmdProp(index = 5)
    private int accessCfg; // 读卡器类型

    @CmdProp(index = 6)
    private int pairAcrNumber; // Use -1 if not paired reader.

    @CmdProp(index = 7)
    private int rdrSio; // Reader link: the SIO number on the SCP that contains the reader. Use - 1 for not used.

    @CmdProp(index = 8)
    private int rdrNumber;  // 0 ~ nReaders-1

    // 锁
    @CmdProp(index = 9)
    private int strkSio; // Strike link: the SIO number on the SCP that contains the strike relay. 0 ~nSio -1. Use -1 for not used.

    @CmdProp(index = 10)
    private int strkNumber; // Strike link: Relay number on the specified SIO (strk_sio). 0 ~ nOutputs -1. Use -1 for not used.

    @CmdProp(index = 11)
    private int strikeTimeMin; // 开门时间 Minimum strike activation time, in seconds. A typical value is 1 second; valid values are 1 to


    @CmdProp(index = 12)
    private int strikeTimeMax; // 开门提示时间 IMaximum strike activation, in seconds; valid values are strike_t_min to 255.


    @CmdProp(index = 13)
    private int strikeMode; // 门磁上电

    // 门

    @CmdProp(index = 14)
    private int doorSio = -1;  //Door contact link: the SIO number on the SCP that contains the input. 0 ~  nSio -1. Use -1 for not used.

    @CmdProp(index = 15)
    private int doorNumber = -1; // Door contact link: Input number on the specified SIO (door_sio). 0 ~ nInputs -1. Use -1 for not used.

    @CmdProp(index = 16)
    private int dcHeld; // Delay before held open alarm is reported (2 second units). Valid values are 1 to 32767.

    // 出门按钮
    @CmdProp(index = 17)
    private int rex0Sio = -1; //Rex-0 link: the SIO number on the SCP that contains the input. 0~nSio -1. Use -1 for not used.

    @CmdProp(index = 18)
    private int rex0Number = -1; // Rex-0 link: Input number on the specified SIO (rex0_sio). 0 ~ nInputs -1. Use -1 for not used.

    // REX 1 is normally not used.
    @CmdProp(index = 19)
    private int rex1Sio = -1; // Rex-1 link: the SIO number on the SCP that contains the input. 0 ~ nSio -1. Use -1 for not configured.

    @CmdProp(index = 20)
    private int rex1Number = -1; //Rex-1 link: Input number on the specified SIO (rex1_sio). 0 ~ nInputs -1. Use -1 for not used.

    @CmdProp(index = 21)
    private int rex0TzMask = 0; //Time zone for disabling rex0 and rex1. Set to 0 to not disable the rex on a time zone.

    @CmdProp(index = 22)
    private int rex1TzMask = 0;

    @CmdProp(index = 23)
    private int altrdrSio = -1; // Alternate reader link: the SIO number on the SCP that contains the reader. Use -1 for not used.

    @CmdProp(index = 24)
    private int altrdrNumber = -1; // 0 ~ nReaders

    @CmdProp(index = 25)
    private int altrdrSpec;

    @CmdProp(index = 26)
    private int cdFormat;  // 卡格式目录

    @CmdProp(index = 27)
    private int apbMode;

    @CmdProp(index = 28)
    private int apbIn;

    @CmdProp(index = 29)
    private int apbTo;

    @CmdProp(index = 30)
    private int spare;

    @CmdProp(index = 31)
    private int actlFlags;

    @CmdProp(index = 32)
    private int offlineMode;

    @CmdProp(index = 33)
    private int defaultMode;

    @CmdProp(index = 34)
    private int defaultLedMode;

    @CmdProp(index = 35)
    private int preAlarm = 0;

    @CmdProp(index = 36)
    private int apbDelay;   // apb延时 0~65535，seconds

    @CmdProp(index = 37)
    private int strkT2;   // ADA开门时间

    @CmdProp(index = 38)
    private int dcHeld2;   // ADA开门过长报警时间


    // 暂时不用
    @CmdProp(index = 39)
    private int strkFollowPulse = 0;
    @CmdProp(index = 40)
    private int strkFollowDelay = 0;
    @CmdProp(index = 41)
    private int nAuthModFlags = 0;

    @CmdProp(index = 42)
    private int nExtFeatureType = 0;

    @CmdProp(index = 43)
    private int iIPBSio = -1; // SIO ID for Interior Push Button (not needed for native locksets). Set to -1 when not used.
    @CmdProp(index = 44)
    private int iIPBNumber = 0; // Input number for Interior Push Button (not needed for native locksets)
    @CmdProp(index = 45)
    private int iIPBLongPress = 0; // IPB long-press, 0-15 seconds (if applicable)
    @CmdProp(index = 46)
    private int iIPBOutSio = 0; // SIO ID for IPB indicator output (not needed for native locksets). Set to -1 when not used.
    @CmdProp(index = 47)
    private int iIPBOutNum = 0;

    @CmdProp(index = 48)
    private int dfofFilterTime;  // 0~65535

    public static ACRConfig fromDb(DevReaderDetail detail) {
        ACRConfig result = new ACRConfig();
        result.setScpNumber(detail.getControllerId());
        result.setRdrSio(detail.getPDeviceId());
        result.setRdrNumber(detail.getReaderNumber());
        result.setAcrNumber(detail.getAcrNumber());
        result.setPairAcrNumber(detail.getPairAcrNumber());

        result.setAccessCfg(detail.getAccessCfg());
        result.setStrikeMode(detail.getStrikeMode());
        result.setStrikeTimeMin(detail.getStrikeTimeMin());
        result.setStrikeTimeMax(detail.getStrikeTimeMax());
        result.setDcHeld(detail.getDcHeld());

        result.setStrkSio(detail.getStrkSio());
        result.setStrkNumber(detail.getStrkNumber());

        result.setDoorSio(detail.getDoorSio());
        result.setDoorNumber(detail.getDoorNumber());

        result.setRex0Sio(detail.getRex0Sio());
        result.setRex0Number(detail.getRex0Number());
        result.setRex0TzMask(detail.getRex0Tz());
        result.setRex1Sio(detail.getRex1Sio());
        result.setRex1Number(detail.getRex1Number());
        result.setRex1TzMask(detail.getRex1Tz());

        // 备用读卡器启用配置
        int altrdrSpec = detail.getAltrdrSpec();
        if (altrdrSpec != 0) {
            result.setAltrdrSio(detail.getAltrdrSio());
            result.setAltrdrNumber(detail.getAltrdrNumber());
        }

        result.setCdFormat(detail.getCdFormat());

        result.setApbMode(detail.getApbMode());
        result.setApbIn(detail.getApbIn());
        result.setApbTo(detail.getApbOut());

        result.setSpare(detail.getSpare());
        result.setActlFlags(detail.getActlFlags());
        result.setOfflineMode(detail.getOfflineMode());
        result.setDefaultLedMode(detail.getDefaultMode());
        result.setStrkT2(detail.getStrkT2());
        result.setDcHeld2(detail.getDcHeld2());
        result.setNExtFeatureType(detail.getFeatureType());
        result.setDfofFilterTime(detail.getFilterAlarm());

        return result;
    }
}
