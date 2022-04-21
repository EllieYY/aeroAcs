package com.wim.aero.acs;

import com.wim.aero.acs.model.mq.AccessMessage;
import com.wim.aero.acs.model.mq.LogMessage;
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
        AccessMessage message = new AccessMessage(1001, new Date().getTime(),
                404, 9, 1, 3, 0, "123456", "");
        String jsonMsg = JsonUtil.toJson(message);
        System.out.println(jsonMsg);
        producer.sendAccessMessage(message);
    }
}
