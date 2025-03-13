package com.basic.myspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfig {
    @Bean
    public CustomVO customVO() {
        return CustomVO.builder() //CustomerVOBuilder
                .mode("테스트 환경")
                .rate(0.5)
                .build(); //CustomerVO

    }
}