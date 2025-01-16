package com.jiehfut.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 两个独立的微服务之间的调用
 * 如何让 80 端口调用 8001 端口呢
 * RestTemplate 提供了多种便捷的访问远程 Http 服务的方法
 * 是一种简单便捷的访问 restful 服务的模板类，是 Spring提供的用于访问 Rest服务的客户端模板工具集
 * 使用restTemplate访问restful接口非常的简单粗暴无脑。
 * getForEnity && getForObject
 *
 * (url, requestMap, ResponseBean.class)这三个参数分别代表
 * REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。
 *
 */

@SpringBootApplication
public class Main80 {
    public static void main(String[] args) {
        // 主启动类
        SpringApplication.run(Main80.class,args);
    }

}