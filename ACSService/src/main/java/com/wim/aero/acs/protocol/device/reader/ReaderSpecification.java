package com.wim.aero.acs.protocol.device.reader;

import com.wim.aero.acs.db.entity.DevReaderDetail;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * dtFmt:
 * | Define      | value | Card data format description   |
 * |-------------+-------+--------------------------------|
 * | IDRDR_D1D0  | 0x01  | Data 1/data 0, Wiegand pulses
 * | IDRDR_ZTRIM | 0x02  | Trim zero bits at beginning and end of card data
 * | IDRDR_T2FMT | 0x04  | Format to nibble array (mag stripe track 2 data decode)
 * | IDRDR_BIDIR | 0x08  | Allow bi-directional Mag decode
 * ------------------------------------------------------|
 * keypadMode:
 * 0 IDRDR_K_NONE
 * 2 IDRDR_K_HID
 * 3 IDRDR_K_INDALA
 * 6 IDRDR_K_4BIT_ALIVE_60
 * 7 IDRDR_K_8BIT_ALIVE_60
 * 8 IDRDR_K_4BIT_ALIVE_10
 * 9 IDRDR_K_8BIT_ALIVE_10
 * ------------------------------------------------------|
 * ledDriveMode:
 * 1  IDRDR_L_BICOLOR
 * 7  IDRDR_L_OSDP
 * 10 IDRDR_L_CLR_OSDP
 * -----------------------------------------------------|
 *
 *
 */

/**
 * @title: ReaderSpecification
 * @author: Ellie
 * @date: 2022/03/11 14:40
 * @description: 6.7 Command 112: Reader Specification
 **/
@Data
public class ReaderSpecification extends Operation {
    @CmdProp(index = 2, defaultValue = "0")
    private Integer lastModified = 0;

    @CmdProp(index = 3)
    private Integer scpNumber;

    @CmdProp(index = 4)
    private Integer sioNumber;

    @CmdProp(index = 5)
    private Integer reader; // 0 to nReaders -1 (Command 109)

    @CmdProp(index = 6, defaultValue = "1")
    private Integer dtFmt = 0x01;  // 目前未做OSDP相关配置，默认韦根模式

    @CmdProp(index = 7, defaultValue = "0")
    private Integer keypadMode;

    @CmdProp(index = 8, defaultValue = "1")
    private Integer ledDriveMode = 1;

    @CmdProp(index = 9, defaultValue = "8")
    private Integer osdpFlags = 0;

    public static ReaderSpecification fromDb(DevReaderDetail detail) {
        ReaderSpecification result = new ReaderSpecification();
        result.setScpNumber(detail.getControllerId());
        result.setSioNumber(detail.getSioNumber());
        result.setReader(detail.getReaderNumber());
        result.setKeypadMode(detail.getKeyMode());

        return result;
    }
}
