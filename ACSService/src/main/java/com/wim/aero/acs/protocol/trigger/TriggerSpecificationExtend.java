package com.wim.aero.acs.protocol.trigger;

import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.model.db.TriggerInfoEx;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @title: TriggerSpecificationExtend
 * @author: Ellie
 * @date: 2022/05/20 14:36
 * @description: 12.3 Command 1117: Trigger Specification (Expanded Code Map)
 **/
@Data
public class TriggerSpecificationExtend extends Operation {
    @CmdProp(index = 2)
    private Integer lastModified = 0;

    @CmdProp(index = 3)
    private Integer scpNumber;  // SCP number

    @CmdProp(index = 4)
    private Integer trgrNumber; // Trigger number

    @CmdProp(index = 5)
    private Integer command; // org.apache.activemq.command.Command to issue to the procedure:
    //    Value Command description
    // 1 Abort a delayed procedure
    // 2 Execute actions with prefix 0
    // 3 Resume a delayed procedure and execute actions with prefix 0
    // 4 Execute actions with prefix 256
    // 5 Execute actions with prefix 512
    // 6 Execute actions with prefix 1024
    // 7 Resume a delayed procedure and execute actions with prefix 256
    // 8 Resume a delayed procedure and execute actions with prefix 512
    // 9 Resume a delayed procedure and execute actions with prefix 1024

    @CmdProp(index = 6)
    private Integer procNum;

    @CmdProp(index = 7)
    private Integer srcType;

    @CmdProp(index = 8)
    private Integer srcNumber;

    @CmdProp(index = 9)
    private Integer tranType;

    @CmdProp(index = 10)
    private Long tranCodeMap0;

    @CmdProp(index = 11)
    private Long tranCodeMap1;

    @CmdProp(index = 12)
    private Integer timezone;

    @CmdProp(index = 13)
    private Integer var1;

    @CmdProp(index = 14)
    private Integer var2;

    @CmdProp(index = 15)
    private Integer var3;

    @CmdProp(index = 16)
    private Integer var4;

    @CmdProp(index = 17)
    private Integer arg1;

    @CmdProp(index = 18)
    private Integer arg2;

    @CmdProp(index = 19)
    private Integer arg3;

    @CmdProp(index = 20)
    private Integer arg4;

    public static TriggerSpecificationExtend fromDb(TriggerInfoEx detail) {
        TriggerSpecificationExtend result = new TriggerSpecificationExtend();
        result.setScpNumber(detail.getControllerId());
        result.setTrgrNumber(detail.getTrgrId());
        result.setCommand(detail.getCommandId());
        result.setProcNum(detail.getProcId());
        result.setTranType(detail.getEventType());
        result.setSrcType(detail.getEventSourceType());
        result.setSrcNumber(detail.getDeviceNumber());

        // codeMap计算
        long tranCodeMap0 = 0;
        long tranCodeMap1 = 0;
        List<Integer> codeList = detail.getCodeList().stream().distinct().collect(Collectors.toList());
        for (Integer code:codeList) {
            if (code == null) {
                continue;
            }

            if (code >= 32) {
                code = code % 32;
                tranCodeMap1 += Math.pow(2, code);
            } else {
                tranCodeMap0 += Math.pow(2, code);
            }
        }

        result.setTranCodeMap0(tranCodeMap0);
        result.setTranCodeMap1(tranCodeMap1);

        result.setTimezone(detail.getSchedulesGroupId());

        result.setVar1(detail.getTrigVar1());
        result.setVar2(detail.getTrigVar2());
        result.setVar3(detail.getTrigVar3());
        result.setVar4(detail.getTrigVar4());

        result.setArg1(detail.getVar1());
        result.setArg2(detail.getVar2());
        result.setArg3(detail.getVar3());
        result.setArg4(detail.getVar4());

        return result;
    }
}
