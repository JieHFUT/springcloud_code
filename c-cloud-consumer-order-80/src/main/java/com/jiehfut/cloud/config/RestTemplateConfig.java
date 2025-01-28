package com.jiehfut.cloud.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;


// RestTemplate 的配置类，将该类注入容器，获得组件
@Configuration
// 下面的注解是进行切换负载均衡算法的行为，对哪一个微服务进行负载均衡的模式修改
@LoadBalancerClient(
        // 下面的 value 值大小写一定要和 consul 里面的名字一样，必须一样
        value = "cloud-payment-service", configuration = RestTemplateConfig.class)
public class RestTemplateConfig {
    @Bean
    @LoadBalanced // consul 默认使用负载均衡方法：使用 @LoadBalanced 注解赋予 RestTemplate 负载均衡的能力
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    // 默认是轮询访问，现在进行切换-更改负载均衡策略为客户端进行随机访问
    @Bean
    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment,
                                                            LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        // 将默认的轮询模式，修改为随机模式
        return new RandomLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
    }
}


// RestTemplate 的配置类，将该类注入容器，获得组件
/* 这是默认的轮询访问
@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced // 默认负载均衡方法：使用 @LoadBalanced 注解赋予 RestTemplate 负载均衡的能力
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}*/

