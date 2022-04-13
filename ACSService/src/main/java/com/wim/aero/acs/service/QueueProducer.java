package com.wim.aero.acs.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.io.Serializable;
import java.util.Date;

/**
 * @title: QueueProducer
 * @author: Ellie
 * @date: 2022/04/12 15:42
 * @description: 生产者
 **/
@Component
@Slf4j
public class QueueProducer {
    private final JmsMessagingTemplate jmsMessagingTemplate;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public QueueProducer(JmsMessagingTemplate jmsMessagingTemplate, ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }


    public void sendMapMessage(String queueName, Object message) {
        threadPoolTaskExecutor.submit(() -> {
            try {
                Destination destination = new ActiveMQQueue(queueName);
                // 这里定义了Queue的key
                ActiveMQMapMessage mqMapMessage = new ActiveMQMapMessage();
                mqMapMessage.setJMSDestination(destination);
                mqMapMessage.setObject("result", message);
                this.jmsMessagingTemplate.convertAndSend(destination, mqMapMessage);
            } catch (Throwable e) {
                log.error("{}", e);
            }
        });
    }

    public void sendObjectMessage(String queueName, Object message) {
        threadPoolTaskExecutor.submit(() -> {
            try {
                log.info("sendObjectMessage:{}",message.toString());
                Destination destination = new ActiveMQQueue(queueName);
                ActiveMQObjectMessage mqObjectMessage = new ActiveMQObjectMessage();
                mqObjectMessage.setJMSDestination(destination);
                mqObjectMessage.setObject((Serializable) message);
                this.jmsMessagingTemplate.convertAndSend(destination, mqObjectMessage);
            } catch (Throwable e) {
                log.error("{}", e);
            }
        });
    }

    public void sendObjectMessage(Destination destination, Object message) {
        threadPoolTaskExecutor.submit(() -> {
            Date date = new Date();
            try {
                // 这里定义了Queue的key
                log.info("【queue-->send】:activeCount={},queueCount={},completedTaskCount={},taskCount={}", threadPoolTaskExecutor.getThreadPoolExecutor().getActiveCount(), threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().size(), threadPoolTaskExecutor.getThreadPoolExecutor().getCompletedTaskCount(), threadPoolTaskExecutor.getThreadPoolExecutor().getTaskCount());

                ActiveMQObjectMessage mqObjectMessage = new ActiveMQObjectMessage();
                mqObjectMessage.setJMSDestination(destination);
                mqObjectMessage.setObject((Serializable) message);
                this.jmsMessagingTemplate.convertAndSend(destination, mqObjectMessage);
            } catch (Throwable e) {
                log.error("{}", e);
            }
        });
    }
}
