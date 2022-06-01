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
    private Integer lastModified = 0;

    @CmdProp(index = 3)
    private Integer nScpId;

    @CmdProp(index = 4)
    private Integer number;   // Card format number (0-7). 跟页面设置不一样

    @CmdProp(index = 5)
    private Integer facility;   // 工程码 Facility code, maximum value is 32 bits (long). Use -1 for not used.

    @CmdProp(index = 6)
    private Integer offset;   // 卡偏移量 This number is a constant that is added to the ID number to allow

    /** 卡类型
     * Define      | Value | Card format table
     * CFMT_F_NULL |  0    | No formatting. See remarks.
     * CFMT_F_WGND |  1    | Wiegand format
     * CFMT_F_MT2  |  2    | Magnetic stripe, ABA Format, 5-bit numeric encoding
     * CFMT_F_MTA  |  3    | Not used
     */
    @CmdProp(index = 7)
    private Integer functionId;

    @CmdProp(index = 8, defaultValue = "1")
    private Integer flags = 1;

    @CmdProp(index = 9)
    private Integer bits; // 格式号？ Number of bits on the card

    @CmdProp(index = 10)
    private Integer peLn; // 偶校验长度 Number of bits to sum for even parity

    @CmdProp(index = 11)
    private Integer peLoc; // Even parity starting bit address

    @CmdProp(index = 12)
    private Integer poLn; // 奇校验长度 Number of bits to sum for odd parity

    @CmdProp(index = 13)
    private Integer poLoc; // Odd parity starting bit address

    @CmdProp(index = 14)
    private Integer fcLn; // 工程码长度 Number of facility code bits

    @CmdProp(index = 15)
    private Integer fcLoc; // 工程码开始位 Facility code starting bit address

    @CmdProp(index = 16)
    private Integer chLn; // 卡号长度 Number of cardholder ID bits

    @CmdProp(index = 17)
    private Integer chLoc; // 卡号开始位 Cardholder ID starting bit address (ms bit)

    @CmdProp(index = 18)
    private Integer icLn;  // Number of issue code bits

    @CmdProp(index = 19)
    private Integer icLoc;

    public static WiegandCardFormat fromDb(int scpiId, CardFormat cardFormat) {
        WiegandCardFormat result = new WiegandCardFormat();
        result.setNScpId(scpiId);
        result.setNumber(cardFormat.getTypeNo());
        result.setFacility(cardFormat.getFacility());
        result.setOffset(cardFormat.getOffset());
        result.setFunctionId(cardFormat.getFunctionId());

        result.setFlags(cardFormat.getFlags());
        result.setBits(cardFormat.getBits());
        result.setPeLn(cardFormat.getPeLn());
        result.setPeLoc(cardFormat.getPeLoc());
        result.setPoLn(cardFormat.getPoLn());
        result.setPoLoc(cardFormat.getPoLoc());

        result.setFcLn(cardFormat.getFcLn());
        result.setFcLoc(cardFormat.getFcLoc());
        result.setChLn(cardFormat.getChLn());
        result.setChLoc(cardFormat.getChLoc());

        result.setIcLn(cardFormat.getIcLn());
        result.setIcLoc(cardFormat.getIcLoc());

        return result;
    }


}
