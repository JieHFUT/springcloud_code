package com.jieHFUT.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Nacos config...
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced // 赋予 RestTemplate 负载均衡的能力
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
