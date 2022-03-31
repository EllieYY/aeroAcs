package com.wim.aero.acs.protocol.accessLevel;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.List;

/**
 * @title: AccessException
 * @author: Ellie
 * @date: 2022/03/31 09:04
 * @description: 10.10 Command 1220: Access Exception List
 **/
@Data
public class AccessLevelException extends Operation {
    @CmdProp(index = 2)
    private int lastModified;

    @CmdProp(index = 3)
    private int scp_number;

    @CmdProp(index = 4)
    private int nCardholderId;

    @CmdProp(index = 5)
    private int nEntries;

    @CmdProp(index = 6)
    private List<ReaderTz> readerTzList;
}
