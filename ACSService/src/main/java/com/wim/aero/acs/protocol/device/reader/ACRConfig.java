package com.wim.aero.acs.protocol.device.reader;

import com.wim.aero.acs.db.entity.DevReaderDetail;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.List;
import java.util.Optional;

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
    private Integer lastModified = 0;

    @CmdProp(index = 3)
    private Integer scpNumber;

    @CmdProp(index = 4)
    private Integer acrNumber;

    @CmdProp(index = 5)
    private Integer accessCfg; // 读卡器类型

    @CmdProp(index = 6, defaultValue = "-1")
    private Integer pairAcrNumber = -1; // Use -1 if not paired reader.

    @CmdProp(index = 7, defaultValue = "-1")
    private Integer rdrSio; // Reader link: the SIO number on the SCP that contains the reader. Use - 1 for not used.

    @CmdProp(index = 8, defaultValue = "-1")
    private Integer rdrNumber;  // 0 ~ nReaders-1

    // 锁
    @CmdProp(index = 9, defaultValue = "-1")
    private Integer strkSio; // Strike link: the SIO number on the SCP that contains the strike relay. 0 ~nSio -1. Use -1 for not used.

    @CmdProp(index = 10, defaultValue = "-1")
    private Integer strkNumber; // Strike link: Relay number on the specified SIO (strk_sio). 0 ~ nOutputs -1. Use -1 for not used.

    @CmdProp(index = 11, defaultValue = "3")
    private Integer strikeTimeMin; // 开门时间 Minimum strike activation time, in seconds. A typical value is 1 second; valid values are 1 to


    @CmdProp(index = 12)
    private Integer strikeTimeMax; // 开门提示时间 IMaximum strike activation, in seconds; valid values are strike_t_min to 255.


    @CmdProp(index = 13, defaultValue = "0")
    private Integer strikeMode; // 门磁上电

    // 门

    @CmdProp(index = 14, defaultValue = "-1")
    private Integer doorSio = -1;  //Door contact link: the SIO number on the SCP that contains the input. 0 ~  nSio -1. Use -1 for not used.

    @CmdProp(index = 15, defaultValue = "-1")
    private Integer doorNumber = -1; // Door contact link: Input number on the specified SIO (door_sio). 0 ~ nInputs -1. Use -1 for not used.

    @CmdProp(index = 16, defaultValue = "1")
    private Integer dcHeld; // Delay before held open alarm is reported (2 second units). Valid values are 1 to 32767.

    // 出门按钮
    @CmdProp(index = 17, defaultValue = "-1")
    private Integer rex0Sio = -1; //Rex-0 link: the SIO number on the SCP that contains the input. 0~nSio -1. Use -1 for not used.

    @CmdProp(index = 18, defaultValue = "-1")
    private Integer rex0Number = -1; // Rex-0 link: Input number on the specified SIO (rex0_sio). 0 ~ nInputs -1. Use -1 for not used.

    // REX 1 is normally not used.
    @CmdProp(index = 19, defaultValue = "-1")
    private Integer rex1Sio = -1; // Rex-1 link: the SIO number on the SCP that contains the input. 0 ~ nSio -1. Use -1 for not configured.

    @CmdProp(index = 20, defaultValue = "-1")
    private Integer rex1Number = -1; //Rex-1 link: Input number on the specified SIO (rex1_sio). 0 ~ nInputs -1. Use -1 for not used.

    @CmdProp(index = 21)
    private Integer rex0TzMask = 0; //Time zone for disabling rex0 and rex1. Set to 0 to not disable the rex on a time zone.

    @CmdProp(index = 22)
    private Integer rex1TzMask = 0;

    @CmdProp(index = 23, defaultValue = "-1")
    private Integer altrdrSio = -1; // Alternate reader link: the SIO number on the SCP that contains the reader. Use -1 for not used.

    @CmdProp(index = 24, defaultValue = "-1")
    private Integer altrdrNumber = -1; // 0 ~ nReaders

    @CmdProp(index = 25)
    private Integer altrdrSpec;

    @CmdProp(index = 26)
    private Integer cdFormat;  // 卡格式目录

    @CmdProp(index = 27)
    private Integer apbMode;

    @CmdProp(index = 28)
    private Integer apbIn = -1;

    @CmdProp(index = 29)
    private Integer apbTo = -1;

    @CmdProp(index = 30)
    private Integer spare = 0;

    @CmdProp(index = 31)
    private Integer actlFlags;

    @CmdProp(index = 32)
    private Integer offlineMode;

    @CmdProp(index = 33)
    private Integer defaultMode;

    @CmdProp(index = 34)
    private Integer defaultLedMode;

    @CmdProp(index = 35)
    private Integer preAlarm = 0;

    @CmdProp(index = 36)
    private Integer apbDelay;   // apb延时 0~65535，seconds

    @CmdProp(index = 37)
    private Integer strkT2;   // ADA开门时间

    @CmdProp(index = 38)
    private Integer dcHeld2;   // ADA开门过长报警时间

//    // 暂时不用
//    @CmdProp(index = 39)
//    private Integer strkFollowPulse = 0;
//    @CmdProp(index = 40)
//    private Integer strkFollowDelay = 0;
//    @CmdProp(index = 41)
//    private Integer nAuthModFlags = 0;

//    @CmdProp(index = 42)
//    private Integer nExtFeatureType = 0;
//
//    @CmdProp(index = 43)
//    private Integer iIPBSio = -1; // SIO ID for Interior Push Button (not needed for native locksets). Set to -1 when not used.
//    @CmdProp(index = 44)
//    private Integer iIPBNumber = 0; // Input number for Interior Push Button (not needed for native locksets)
//    @CmdProp(index = 45)
//    private Integer iIPBLongPress = 0; // IPB long-press, 0-15 seconds (if applicable)
//    @CmdProp(index = 46)
//    private Integer iIPBOutSio = 0; // SIO ID for IPB indicator output (not needed for native locksets). Set to -1 when not used.
//    @CmdProp(index = 47)
//    private Integer iIPBOutNum = 0;
//
//    @CmdProp(index = 48)
//    private Integer dfofFilterTime = 0;  // 0~65535

    public static ACRConfig fromDb(DevReaderDetail detail) {
        ACRConfig result = new ACRConfig();
        result.setScpNumber(detail.getControllerId());
        result.setRdrSio(detail.getSioNumber());
        result.setRdrNumber(detail.getReaderNumber());
        result.setAcrNumber(detail.getAcrNumber());

        if (detail.getAccessCfg() == null || detail.getAccessCfg() == 0) {
            result.setPairAcrNumber(-1);
        } else {
            result.setPairAcrNumber(detail.getPairAcrNumber());
        }

        result.setAccessCfg(detail.getAccessCfg());
        result.setStrikeMode(detail.getStrikeMode());
        result.setStrikeTimeMin(detail.getStrikeTimeMin());
        result.setStrikeTimeMax(detail.getStrikeTimeMax());

        // 2s unit
        Integer dcHeld = Optional.ofNullable(detail.getDcHeld()).orElse(2);
        result.setDcHeld(dcHeld / 2);

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
        int altrdrSpec = Optional.ofNullable(detail.getAltrdrSpec()).orElse(0);
        if (altrdrSpec != 0) {
            result.setAltrdrSio(detail.getAltrdrSio());
            result.setAltrdrNumber(detail.getAltrdrNumber());
        }

        // TODO:
//        result.setCdFormat(detail.getCdFormat());
        result.setCdFormat(255);

        result.setApbMode(detail.getApbMode());
        result.setApbIn(detail.getApbIn());
        result.setApbTo(detail.getApbOut());

        result.setSpare(detail.getSpare());
        result.setActlFlags(detail.getActlFlags());
        result.setOfflineMode(detail.getOfflineMode());
        result.setDefaultMode(detail.getDefaultMode());
        result.setStrkT2(detail.getStrkT2());

        Integer dcHeld2 = Optional.ofNullable(detail.getDcHeld2()).orElse(2);
        result.setDcHeld2(dcHeld2 / 2);
//        result.setNExtFeatureType(detail.getFeatureType());
//        result.setDfofFilterTime(detail.getFilterAlarm());

        return result;
    }
}
