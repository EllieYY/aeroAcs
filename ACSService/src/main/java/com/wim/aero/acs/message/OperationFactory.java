package com.wim.aero.acs.message;

import com.wim.aero.acs.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @title: MessageFactory
 * @author: Ellie
 * @date: 2022/03/21 09:02
 * @description:
 **/
@Service
public class OperationFactory {
//    @Autowired(required = false)
    Map<String, Operation> operationMap = new HashMap<>();

    public Operation getOperation(int key) {
        return operationMap.get(Constants.OPERATION_PREFIX + key);
    }
}
