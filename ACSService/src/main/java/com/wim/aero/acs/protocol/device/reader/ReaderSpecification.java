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
    @CmdProp(index = 2)
    private int lastModified = 0;

    @CmdProp(index = 3)
    private int scpNumber;

    @CmdProp(index = 4)
    private int sioNumber;

    @CmdProp(index = 5)
    private int reader; // 0 to nReaders -1 (Command 109)

    @CmdProp(index = 6)
    private int dtFmt = 0x01;

    @CmdProp(index = 7)
    private int keypadMode;

    @CmdProp(index = 8)
    private int ledDriveMode = 1;

    @CmdProp(index = 9)
    private int osdpFlags = 0;

    public static ReaderSpecification fromDb(DevReaderDetail detail) {
        ReaderSpecification result = new ReaderSpecification();
        result.setScpNumber(detail.getControllerId());
        result.setSioNumber(detail.getPDeviceId());
        result.setReader(detail.getReaderNumber());
        result.setKeypadMode(detail.getKeyMode());

        // TODO:目前未做OSDP相关配置，默认韦根模式

        return result;
    }
}
