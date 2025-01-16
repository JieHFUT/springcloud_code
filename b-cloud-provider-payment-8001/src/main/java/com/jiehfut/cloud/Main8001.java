package com.jiehfut.cloud;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jiehfut.cloud.mapper") // 一次性扫描全部 mapper
public class Main8001 {

    public static void main(String[] args) {
        SpringApplication.run(Main8001.class, args);
    }

}
