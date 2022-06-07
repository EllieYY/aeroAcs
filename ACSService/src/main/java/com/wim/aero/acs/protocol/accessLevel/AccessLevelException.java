package com.wim.aero.acs.protocol.accessLevel;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: AccessException
 * @author: Ellie
 * @date: 2022/03/31 09:04
 * @description: 10.10 Command 1220: Access Exception List
 **/
@Data
public class AccessLevelException extends Operation {
    @CmdProp(index = 2, defaultValue = "0")
    private int lastModified = 0;

    @CmdProp(index = 3)
    private int scp_number;

    @CmdProp(index = 4)
    private String nCardholderId;

    @CmdProp(index = 5, defaultValue = "0")
    private int nEntries;  // up to 64 entries are supported. A value of 0 removes the list.

    @CmdProp(index = 6, enCodec = "formatReaderTz")
    private List<ReaderTz> readerTzList = new ArrayList<>();


    public AccessLevelException(int scpId, String nCardholderId, int tz, boolean block) {
        this.scp_number = scpId;
        this.nCardholderId = nCardholderId;

        if (block) {
            nEntries = 64;
            for (int i = 0; i < nEntries; i++) {
                readerTzList.add(new ReaderTz(i, tz));
            }
        } else {
            nEntries = 0;
            readerTzList.clear();
        }
    }
}
