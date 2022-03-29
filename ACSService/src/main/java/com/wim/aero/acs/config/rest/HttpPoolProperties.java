package com.wim.aero.acs.config.rest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description : httpclient连接池配置信息
 * @Author : Ellie
 * @Date : 2019/2/15
 */
@Component
@ConfigurationProperties(prefix = "httppool")
@Data
public class HttpPoolProperties {
    private Integer maxTotal;
    private Integer defaultMaxPerRoute;
    private Integer connectTimeout;
    private Integer connectionRequestTimeout;
    private Integer socketTimeout;
    private Integer validateAfterInactivity;
}
