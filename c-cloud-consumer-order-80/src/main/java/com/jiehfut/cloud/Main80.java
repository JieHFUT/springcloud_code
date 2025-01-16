package com.jiehfut.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 如何让 80 端口调用 8001 端口呢
 *
 */

@SpringBootApplication
public class Main80 {
    public static void main(String[] args) {
        // 主启动类
        SpringApplication.run(Main80.class,args);
    }

}