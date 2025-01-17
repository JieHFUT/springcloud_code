package com.jiehfut.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


// RestTemplate 的配置类，将该类注入容器，获得组件
@Configuration
public class RestTemplateConfig
{
    @Bean
    @LoadBalanced // consul 天生支持负载均衡
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

}