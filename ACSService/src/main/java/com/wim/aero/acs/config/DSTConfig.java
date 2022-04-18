package com.wim.aero.acs.config;

import com.wim.aero.acs.model.DST;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @title: DSTConfig
 * @author: Ellie
 * @date: 2022/04/15 15:00
 * @description:
 **/
@Data
@Component
@ConfigurationProperties(prefix = "dst") // 配置 文件的前缀
public class DSTConfig {
    private List<DST> list;
}
