package com.wim.aero.acs.config.mq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

/**
 * @title: QueueConfig
 * @author: Ellie
 * @date: 2022/04/13 09:29
 * @description:
 **/
@Configuration
public class QueueConfig {
    @Value("${queueName.accessQueue}")
    private String accessQueueName;

    @Value("${queueName.alarmQueue}")
    private String alarmQueueName;

    @Value("${queueName.logQueue}")
    private String logQueueName;

    @Bean("accessQueue")
    public Queue accessQueue() {
        return new ActiveMQQueue(accessQueueName);
    }

    @Bean("alarmQueue")
    public Queue alarmQueue() {
        return new ActiveMQQueue(alarmQueueName);
    }

    @Bean("logQueue")
    public Queue logQueue() {
        return new ActiveMQQueue(logQueueName);
    }
}
