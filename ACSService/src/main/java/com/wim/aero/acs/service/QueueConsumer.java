package com.wim.aero.acs.service;

import com.wim.aero.acs.model.mq.ScpSeqMessage;
import com.wim.aero.acs.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @title: QueueConsumer
 * @author: Ellie
 * @date: 2022/04/13 16:39
 * @description:
 **/
@Service
@Configuration
@Slf4j
public class QueueConsumer {
    private final RequestPendingCenter requestPendingCenter;

    @Autowired
    public QueueConsumer(RequestPendingCenter requestPendingCenter) {
        this.requestPendingCenter = requestPendingCenter;
    }

    @JmsListener(destination = "scpSeqQueue", containerFactory = "MyJmsQueueListener")
    public void receiveMsg(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage)message;

//        message.acknowledge();

        ScpSeqMessage messageObj = JsonUtil.fromJson(textMessage.getText(), ScpSeqMessage.class);
        if (messageObj == null) {
            log.error("[mq消息解析错误] {}", message);
            return;
        }

        log.info("[mq消息消费] {} - {} : {}", messageObj.getScpId(), messageObj.getSeq(), messageObj.getStatus());
//        requestPendingCenter.commandResponse(messageObj);
//        session.recover();    // 重发

        if (requestPendingCenter.commandResponse(messageObj)) {
            // 检查发送次数
            message.acknowledge();
        } else {

            session.recover();    // 重发
        }
    }
}
