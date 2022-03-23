package com.wim.aero.acs.message;

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

}
