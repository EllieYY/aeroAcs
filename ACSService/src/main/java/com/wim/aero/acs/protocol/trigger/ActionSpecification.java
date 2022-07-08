package com.wim.aero.acs.protocol.trigger;

import com.wim.aero.acs.db.entity.TrigScpProcDetail;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @title: ActionSpecification
 * @author: Ellie
 * @date: 2022/05/20 14:37
 * @description: 12.4 Command 118: Action Specification
 **/
@Data
public class ActionSpecification extends Operation {
    @CmdProp(index = 2)
    private Integer lastModified = 0;  // Set to 0

    @CmdProp(index = 3)
    private Integer scpNumber;   // SCP number

    @CmdProp(index = 4)
    private Integer procNumber; // 0 to nProc-1,

    @CmdProp(index = 5)
    private String actionType;   // 需要加prefix

    @CmdProp(index = 6, enCodec = "formatStrList")
    private List<String> paramList = new ArrayList<>();

    public static ActionSpecification fromDb(TrigScpProcDetail detail) {
        ActionSpecification result = new ActionSpecification();
        result.setScpNumber(detail.getControllerId());
        result.setProcNumber(detail.getProcId());

        int prefix = Optional.ofNullable(detail.getPrefix()).orElse(0);
        int actionType = detail.getFunctionId();
        result.setActionType(Integer.toString(prefix) + Integer.toString(actionType));

        List<String> allParamList = new ArrayList<>();
        allParamList.add(Optional.ofNullable(detail.getPara01()).orElse(0).toString());
        allParamList.add(Optional.ofNullable(detail.getPara02()).orElse(0).toString());
        allParamList.add(Optional.ofNullable(detail.getPara03()).orElse(0).toString());

        if (actionType == ActionType.CMD_339.getType()) {
            allParamList.add(detail.getPara09());
        } else {
            allParamList.add(Optional.ofNullable(detail.getPara04()).orElse(0).toString());
        }
        allParamList.add(Optional.ofNullable(detail.getPara05()).orElse(0).toString());
        allParamList.add(Optional.ofNullable(detail.getPara06()).orElse(0).toString());
        allParamList.add(Optional.ofNullable(detail.getPara07()).orElse(0).toString());
        allParamList.add(Optional.ofNullable(detail.getPara08()).orElse(0).toString());

        int paramLegnth = ActionType.fromType(actionType).getLength();
        paramLegnth = paramLegnth > allParamList.size() ? allParamList.size() : paramLegnth;
        result.setParamList(allParamList.subList(0, paramLegnth));

        return result;
    }

}
