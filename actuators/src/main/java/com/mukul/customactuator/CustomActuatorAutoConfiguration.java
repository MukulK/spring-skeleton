package com.mukul.customactuator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomActuatorAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(CustomHealthEndpoint.class)
    public CustomHealthEndpoint customHealthEndpoint() {
        return new CustomHealthEndpoint();
    }
}