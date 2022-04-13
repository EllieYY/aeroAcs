package com.wim.aero.acs;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableKnife4j
@MapperScan("com.wim.aero.acs.db.mapper")
public class AcsserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcsserviceApplication.class, args);
    }

}
