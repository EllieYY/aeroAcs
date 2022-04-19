package com.wim.aero.acs.protocol.device;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/**
 * @title: SCPCapacity 设备参数
 * @author: Ellie
 * @date: 2022/03/10 15:53
 * @description: 3.7 Command 1107: SCP Device Specification
 * 按最大值去做默认配置，没有页面配置入口
 **/
@Data
public class SCPSpecification extends Operation {
    @CmdProp(index = 3)
    private int scpNumber;

    // 无需配置
    @CmdProp(index = 2)
    private int lastModified = 0;
    @CmdProp(index = 4)
    private int serNumLow = 0;
    @CmdProp(index = 5)
    private int serNumHigh = 0;
    @CmdProp(index = 6)
    private int revMajor = 0;
    @CmdProp(index = 7)
    private int revMinor = 0;

    /** 485端口个数，X1100有2个 */
    @CmdProp(index = 8)
    private int nMsp1Port = 2;

    @CmdProp(index = 9)
    private long nTransactions = 80000;

    @CmdProp(index = 10)
    private int nSio = 33;  // Number of SIOs - 16 maximum per RS-485 port，需要加上内置SIO板1块

    @CmdProp(index = 11)
    private int nMp = 615;   // Number of monitor points - 615 maximum (32 x X200’s = 608 + 7 (1100) = 615)

    @CmdProp(index = 12)
    private int nCp = 388;   // Number of control points - 388 maximum (32 x X300’s = 384 + 4 (1100) = 388)

    @CmdProp(index = 13)
    private int nAcr = 64;  // Number of access control readers - 64 maximum

    @CmdProp(index = 14)
    private int nAlvl = 32000; // Number of access levels - 32000 maximum

    @CmdProp(index = 15)
    private int nTrgr = 1024; // Number of triggers - 1024 maximum

    @CmdProp(index = 16)
    private int nProc = 1024; // Number of procedures - 1024 maximum

    @CmdProp(index = 17)
    private int gmtOffset = -28800;  // Beijing

    @CmdProp(index = 18)
    private int nDstID = 100;  // 夏令时 0-不使用 100-使用

    @CmdProp(index = 19)
    private int nTz = 255; // Number of time zones (schedules) - 255 maximum

    @CmdProp(index = 20)
    private int nHol = 255; // Number of holidays - 255 maximum

    @CmdProp(index = 21)
    private int nMpg = 128; // Number of monitor point groups, up to 128 groups

    @CmdProp(index = 22)
    private int nTranLimit = 0; // Number of unreported transactions to log transaction (long).

    @CmdProp(index = 23)
    private int nAuthModType = 0; // Auxiliary Authentication Module Type to use. 0 = None.

    @CmdProp(index = 24)
    private int nOperModes = 0; // Number of operating modes 1-8 (0 = default of 1)

    @CmdProp(index = 25)
    private int operType = 0; // Operating mode type (0 = Access Level Mappings, 1 = Access Level Assignments)

    @CmdProp(index = 26)
    private int nLanguages = 0; // Number of languages supported 0-16 (0 = default of 1)

    @CmdProp(index = 27)
    private int nSrvcType = 0;

    public SCPSpecification(int scpNumber) {
        this.scpNumber = scpNumber;
    }
}
