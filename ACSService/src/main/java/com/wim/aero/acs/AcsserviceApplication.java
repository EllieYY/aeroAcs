package com.wim.aero.acs;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableKnife4j
@EnableJms
@EnableAsync
@MapperScan("com.wim.aero.acs.db.mapper")
public class AcsserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcsserviceApplication.class, args);
    }

}
