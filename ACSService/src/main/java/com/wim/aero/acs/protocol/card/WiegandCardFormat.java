package com.wim.aero.acs.protocol.card;

import com.wim.aero.acs.db.entity.CardFormat;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * @title: CardFormat
 * @author: Ellie
 * @date: 2022/03/14 09:29
 * @description: 控制器卡格式设置 8.7 Command 1102: Card Formatter Configuration
 * 添加控制器 - 卡格式
 **/
@Data
public class WiegandCardFormat extends Operation {

    @CmdProp(index = 2)
    private int lastModified = 0;

    @CmdProp(index = 3)
    private int nScpId;

    @CmdProp(index = 4)
    private int number;   // Card format number (0-7). 跟页面设置不一样

    @CmdProp(index = 5)
    private int facility;   // 工程码 Facility code, maximum value is 32 bits (long). Use -1 for not used.

    @CmdProp(index = 6)
    private int offset;   // 卡偏移量 This number is a constant that is added to the ID number to allow

    /** 卡类型
     * Define      | Value | Card format table
     * CFMT_F_NULL |  0    | No formatting. See remarks.
     * CFMT_F_WGND |  1    | Wiegand format
     * CFMT_F_MT2  |  2    | Magnetic stripe, ABA Format, 5-bit numeric encoding
     * CFMT_F_MTA  |  3    | Not used
     */
    @CmdProp(index = 7)
    private int functionId;

    @CmdProp(index = 8)
    private int flags;

    @CmdProp(index = 9)
    private int bits; // 格式号？ Number of bits on the card

    @CmdProp(index = 10)
    private int peLn; // 偶校验长度 Number of bits to sum for even parity

    @CmdProp(index = 11)
    private int peLoc; // Even parity starting bit address

    @CmdProp(index = 12)
    private int poLn; // 奇校验长度 Number of bits to sum for odd parity

    @CmdProp(index = 13)
    private int poLoc; // Odd parity starting bit address

    @CmdProp(index = 14)
    private int fcLn; // 工程码长度 Number of facility code bits

    @CmdProp(index = 15)
    private int fcLoc; // 工程码开始位 Facility code starting bit address

    @CmdProp(index = 16)
    private int chLn; // 卡号长度 Number of cardholder ID bits

    @CmdProp(index = 17)
    private int chLoc; // 卡号开始位 Cardholder ID starting bit address (ms bit)

    @CmdProp(index = 18)
    private int icLn = 0;  // Number of issue code bits

    @CmdProp(index = 19)
    private int icLoc = 0;

    public static WiegandCardFormat fromDb(CardFormat cardFormat) {
        WiegandCardFormat result = new WiegandCardFormat();
        result.setNumber(cardFormat.getTypeNo());
        result.setFacility(cardFormat.getFacility());
        result.setOffset(cardFormat.getOffset());
        result.setFunctionId(cardFormat.getFunctionId());

        result.setBits(cardFormat.getBits());
        result.setPeLn(cardFormat.getPeLn());
        result.setPoLn(cardFormat.getPoLn());

        // TODO:考虑在页面增加配置
//        result.setPeLoc(cardFormat.getPeLoc());
//        result.setPoLoc(cardFormat.getPoLoc());

        result.setFcLn(cardFormat.getFcLn());
        result.setFcLoc(cardFormat.getFcLoc());
        result.setChLn(cardFormat.getChLn());
        result.setChLoc(cardFormat.getChLoc());

        return result;
    }


}
