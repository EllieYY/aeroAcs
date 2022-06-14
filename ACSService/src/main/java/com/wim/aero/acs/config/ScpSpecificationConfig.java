package com.wim.aero.acs.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @title: ScpSpecificationConfig
 * @author: Ellie
 * @date: 2022/06/13 15:17
 * @description:
 **/
@Configuration
@Data
public class ScpSpecificationConfig {
    @Value("${aero-scp-sepcification.rs485Baud}")
    private int baudRate;

    @Value("${aero-scp-sepcification.maxTransactions}")
    private int maxTransactions;

    @Value("${aero-scp-sepcification.maxAcessLevel}")
    private int maxAcessLevel;

    @Value("${aero-scp-sepcification.maxTrigger}")
    private int maxTrigger;

    @Value("${aero-scp-sepcification.maxProcedure}")
    private int maxProcedure;

    @Value("${aero-scp-sepcification.maxTimeZone}")
    private int maxTimeZone;

    @Value("${aero-scp-sepcification.maxHoliday}")
    private int maxHoliday;

    @Value("${aero-scp-sepcification.maxTranLimit}")
    private int maxTranLimit;

    @Value("${aero-scp-sepcification.maxMpg}")
    private int maxMpg;

    @Value("${aero-scp-sepcification.operModes}")
    private int operModes;

    @Value("${aero-scp-sepcification.operTypes}")
    private int operTypes;
}
