package com.wim.aero.acs.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @title: CommServiceInfo
 * @author: Ellie
 * @date: 2022/03/28 11:07
 * @description:
 **/
@Data
@Configuration
public class CommServiceInfo {
    @Value("${comm-service-info.url}")
    private String url;

}
