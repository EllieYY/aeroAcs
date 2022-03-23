package com.wim.aero.acs.protocol.device;

import com.wim.aero.acs.db.entity.DevControllerDetail;
import com.wim.aero.acs.message.MessageBody;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @title: SCP通信参数
 * @author: Ellie
 * @date: 2022/03/10 15:35
 * @description: 3.3 Command 1013:Create SCP
 **/
@Data
public class SCPDriver extends Operation {
    @CmdProp(index = 2)
    private Integer nSCPId;

    @CmdProp(index = 3, defaultValue = "0")
    private Integer address;

    /** 通信类型：
     *  4：IP server - 作为客户端连SCP
     *  7：IP Client - 作为服务端接收SCP连接，
     *     需要设置disabling transactions（+256） | pollDelay = 5000 | address = 0
     *  +256: disabling transactions
     */
    @CmdProp(index = 4, defaultValue = "4")
    private Integer nCommAccess;

    /** 重连次数 */
    @CmdProp(index = 5, defaultValue = "0")
    private Integer eMax;

    /** 轮询间隔 */
    @CmdProp(index = 6, defaultValue = "0")
    private long pollDelay;

    /** 通信地址，格式： ip.port */
    @CmdProp(index = 7, defaultValue = "", enCodec = "formatStr")
    private String cCommString;

    /** 通信认证密码，AES模式下使用 */
    @CmdProp(index = 8, defaultValue = "", enCodec = "formatStr")
    private String cPswdString;

    /** 离线时间，默认0:15000 */
    @CmdProp(index = 9, defaultValue = "0")
    private Integer offlineTime = 0;

    /** 无需设置 */
    @CmdProp(index = 10, defaultValue = "0")
    private Integer nAltPortEnable;
    @CmdProp(index = 11, defaultValue = "0")
    private Integer nAltPortCommAccess;
    @CmdProp(index = 12, defaultValue = "0")
    private Integer nAltPortE_max;
    @CmdProp(index = 13, defaultValue = "0")
    private Integer nAltPortPoll_delay;
    @CmdProp(index = 14, defaultValue = "", enCodec = "formatStr")
    private String cAltPortCommString = "";

    public static SCPDriver fromDb(DevControllerDetail detail) {
        SCPDriver driver = new SCPDriver();
        driver.setNSCPId(detail.getDeviceId());
        driver.setNCommAccess(detail.getConnectType());
        driver.setEMax(detail.getCcTimes());

        driver.setPollDelay(detail.getCcInterval());
        driver.setCCommString(formatIp(detail.getDeviceIp(), detail.getDevicePort()));
        driver.setCPswdString(detail.getPwd());
        driver.setOfflineTime(detail.getWaitTime());
        return driver;
    }

    private static String formatIp(String ip, Integer port) {
        return ip + "." + port;
    }
}
