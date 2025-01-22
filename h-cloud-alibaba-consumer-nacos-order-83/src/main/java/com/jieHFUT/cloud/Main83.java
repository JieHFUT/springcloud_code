package com.jieHFUT.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient // 服务发现与注册
@SpringBootApplication // 主启动
public class Main83 {

    public static void main(String[] args) {

        SpringApplication.run(Main83.class,args);
    }
}