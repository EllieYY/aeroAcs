package com.wim.aero.acs.protocol.device;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * --------------------------------------------
 * portNumber
 * PORT0: used for host communication
 * port1、port2: communicates with SIO panels
 * --------------------------------------------
 * baudRate: 9600, 19200, 38400. A value of -1 indicates 115200.
 * --------------------------------------------
 * Reply timeout
 *      * 0 = 90ms
 *      * value >= 50 && value <= 150
 * --------------------------------------------
 * Type of Protocol
 *      *  0 = HID Aero™ X100, X200 and X300 protocol
 *      * 15 = VertX V100, V200 and V300 protocol
 *      * 16 = Aperio
 * --------------------------------------------
 * Aperio dialect
 *      * 0 = No TLS (Default)
 *      * 1 = Use TLS
 * --------------------------------------------
 */

/**
 * @title: SIODriver
 * @author: Ellie
 * @date: 2022/03/10 16:34
 * @description: 配置SIO的通信驱动 - 添加SIO设备时需要用到
 * 6.2 Command 108: Driver Configuration
 **/
@Data
public class SIODriver extends Operation {
    @CmdProp(index = 2)
    private int lastModified = 0;

    @CmdProp(index = 3)
    private int scpNumber;

    /** unique within the SCP and should be in the range of 0 to (nMsp1Port-1) */
    @CmdProp(index = 4)
    private int msp1Number;

    @CmdProp(index = 5)
    private int portNumber;

    @CmdProp(index = 6)
    private int baudRate = 38400;

    @CmdProp(index = 7)
    private int replyTime = 0;

    @CmdProp(index = 8)
    private int nProtocol = 0;

    @CmdProp(index = 9)
    private int nDialect = 0;

    public SIODriver(int scpNumber, int msp1Number, int portNumber) {
        this.scpNumber = scpNumber;
        this.msp1Number = msp1Number;
        this.portNumber = portNumber;
    }
}
