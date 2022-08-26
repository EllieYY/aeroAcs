package com.wim.aero.acs;

import com.wim.aero.acs.model.mq.AccessMessage;
import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.model.mq.ScpSeqMessage;
import com.wim.aero.acs.service.QueueProducer;
import com.wim.aero.acs.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest(classes = AcsserviceApplication.class)
class AcsserviceApplicationTests {
    @Autowired
    private QueueProducer producer;

    @Test
    void contextLoads() {
        ScpSeqMessage message = new ScpSeqMessage(1001, 404, 9, 1, "123456");
        String jsonMsg = JsonUtil.toJson(message);
        System.out.println(jsonMsg);
//        producer.sendDelayScpMessage(message, 10);
    }
}
