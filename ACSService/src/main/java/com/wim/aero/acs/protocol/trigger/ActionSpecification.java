package com.wim.aero.acs.protocol.trigger;

import com.wim.aero.acs.db.entity.TrigScpProcDetail;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: ActionSpecification
 * @author: Ellie
 * @date: 2022/05/20 14:37
 * @description: 12.4 Command 118: Action Specification
 **/
@Data
public class ActionSpecification extends Operation {
    @CmdProp(index = 2)
    private int lastModified = 0;  // Set to 0

    @CmdProp(index = 3)
    private int scpNumber;   // SCP number

    @CmdProp(index = 4)
    private int procNumber; // 0 to nProc-1,

    @CmdProp(index = 5)
    private String actionType;   // 需要加prefix

    @CmdProp(index = 6, enCodec = "formatList")
    private List<Integer> paramList;

    public static ActionSpecification fromDb(TrigScpProcDetail detail) {
        ActionSpecification result = new ActionSpecification();
        result.setScpNumber(detail.getControllerId());
        result.setProcNumber(detail.getProcId());

        int prefix = detail.getPrefix();
        int actionType = detail.getFunctionId();
        result.setActionType(Integer.toString(prefix) + Integer.toString(actionType));

        List<Integer> allParamList = new ArrayList<>();
        allParamList.add(detail.getPara01());
        allParamList.add(detail.getPara02());
        allParamList.add(detail.getPara03());
        allParamList.add(detail.getPara04());
        allParamList.add(detail.getPara05());
        allParamList.add(detail.getPara06());

        int paramLegnth = ActionType.fromType(actionType).getLength();
        paramLegnth = paramLegnth > allParamList.size() ? allParamList.size() : paramLegnth;
        result.setParamList(allParamList.subList(0, paramLegnth));

        return result;
    }

}
