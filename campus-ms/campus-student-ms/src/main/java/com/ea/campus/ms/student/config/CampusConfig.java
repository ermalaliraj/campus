package com.ea.campus.ms.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CampusConfig {

    public static int TIMEOUT = 60*1000;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
