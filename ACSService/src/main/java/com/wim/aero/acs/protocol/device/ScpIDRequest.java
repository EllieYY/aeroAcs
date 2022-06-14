package com.wim.aero.acs.protocol.device;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.security.DenyAll;

/**
 * @title: ScpIDRequest
 * @author: Ellie
 * @date: 2022/06/14 13:50
 * @description:
 **/
@Data
@AllArgsConstructor
public class ScpIDRequest extends Operation {
    @CmdProp(index = 1)
    private int scpId;
}
