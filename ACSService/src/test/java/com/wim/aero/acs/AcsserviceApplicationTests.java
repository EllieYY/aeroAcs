package com.wim.aero.acs;

import com.wim.aero.acs.model.mq.LogMessage;
import com.wim.aero.acs.service.QueueProducer;
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
        producer.sendLogMessage(new LogMessage(
                1000001, new Date(),120,
                0,1, 0, 1,
                ""));
    }

}
