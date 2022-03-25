package com.wim.aero.acs.message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestMessage extends Message<Operation> {
    public RequestMessage() {
    }

    public RequestMessage(int scpId, Operation operation) {
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setScpId(scpId);
        messageHeader.setMsgType(OperationType.fromOperation(operation).getOpCode());
        this.setMessageHeader(messageHeader);
        this.setMessageBody(operation);
    }

    @Override
    public Class getMessageBodyDecodeClass(int opcode) {
        return OperationType.fromOpCode(opcode).getOperationClazz();
    }

    public static String encode(int scpId, Operation operation) {
        RequestMessage message = new RequestMessage(scpId, operation);
        String msg = message.encode();

        log.info("{} 报文 {}", scpId, msg);
        return msg;
    }

}
