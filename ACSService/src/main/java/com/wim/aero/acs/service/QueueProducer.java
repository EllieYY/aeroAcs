package com.wim.aero.acs.service;

import com.wim.aero.acs.model.mq.AccessMessage;
import com.wim.aero.acs.model.mq.AlarmMessage;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.model.mq.StatusMessage;
import com.wim.aero.acs.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.Queue;
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
    private final Queue accessQueue;
    private final Queue alarmQueue;
    private final Queue logQueue;
    private final Queue statusQueue;

    @Autowired
    public QueueProducer(JmsMessagingTemplate jmsMessagingTemplate, ThreadPoolTaskExecutor threadPoolTaskExecutor,
                         Queue accessQueue, Queue alarmQueue, Queue logQueue, Queue statusQueue) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.accessQueue = accessQueue;
        this.alarmQueue = alarmQueue;
        this.logQueue = logQueue;
        this.statusQueue = statusQueue;
    }

    public void sendLogMessage(LogMessage logMessage) {
        String messageStr = JsonUtil.toJson(logMessage);
        log.info("[日志事件] - {}", messageStr);
        this.sendMessage(logQueue, messageStr);
    }

    public void sendAlarmMessage(AlarmMessage alarmMessage) {
        String messageStr = JsonUtil.toJson(alarmMessage);
        log.info("[报警事件] - {}", messageStr);
        this.sendMessage(alarmQueue, messageStr);
    }

    public void sendAccessMessage(AccessMessage accessMessage) {
        String messageStr = JsonUtil.toJson(accessMessage);
        log.info("[刷卡事件] - {}", messageStr);
        this.sendMessage(accessQueue, messageStr);
    }

    public void sendStatusMessage(StatusMessage statusMessage) {
        String messageStr = JsonUtil.toJson(statusMessage);
        log.info("[状态事件] - {}", messageStr);
        this.sendMessage(statusQueue, messageStr);
    }

    public void sendMessage(Destination destination, String message) {
        threadPoolTaskExecutor.submit(() -> {
            Date date = new Date();
            try {
//                log.info("[mq][queue-->send]:activeCount={},queueCount={},completedTaskCount={},taskCount={}",
//                        threadPoolTaskExecutor.getThreadPoolExecutor().getActiveCount(),
//                        threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().size(),
//                        threadPoolTaskExecutor.getThreadPoolExecutor().getCompletedTaskCount(),
//                        threadPoolTaskExecutor.getThreadPoolExecutor().getTaskCount());

                this.jmsMessagingTemplate.convertAndSend(destination, message);
            } catch (Throwable e) {
                log.error("{}", e);
            }
        });
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
}
