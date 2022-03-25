package com.wim.aero.acs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@MapperScan("com.wim.aero.acs.db.mapper")
public class AcsserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcsserviceApplication.class, args);
    }

}
