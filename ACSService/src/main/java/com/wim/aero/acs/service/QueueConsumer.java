package com.wim.aero.acs.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @title: QueueConsumer
 * @author: Ellie
 * @date: 2022/04/13 16:39
 * @description:
 **/
@Component
public class QueueConsumer {

//    @JmsListener(destination = "logQueue", containerFactory = "queueListener")
//    public void receiveMsg(String text) {
//        System.out.println("接收到消息 : "+text);
//    }
}
