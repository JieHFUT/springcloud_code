package com.jieHFUT.cloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication // 申明启动类
@EnableDiscoveryClient // 服务注册
public class Main9001 {

    public static void main(String[] args) {

        SpringApplication.run(Main9001.class, args);
    }
}
