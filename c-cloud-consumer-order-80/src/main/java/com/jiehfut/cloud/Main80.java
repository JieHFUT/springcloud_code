package com.jiehfut.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 两个独立的微服务之间的调用
 * 如何让 80 端口调用 8001 端口呢
 * RestTemplate API 提供了多种便捷的访问远程 Http 服务的方法
 * 是一种简单便捷的访问 restful 服务的模板类，是 Spring 提供的用于访问 Rest服务的客户端模板工具集
 * 使用 restTemplate 访问 restful接口非常的简单粗暴无脑。
 * getForEnity && getForObject
 *
 * (url, requestMap, ResponseBean.class)这三个参数分别代表
 * REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。(请求路径，请求参数，响应类型)
 *
 * 返回对象为相应体中数据转换为的对象：基本上可以理解为 JSON
 * 返回对象为 ResponseEntity 对象，就是整个响应，包括响应头，响应状态码，响应体等
 */

@SpringBootApplication
@EnableDiscoveryClient // 第三步（第一步在 pom.xml，第二步在 application.yml）
public class Main80 {
    public static void main(String[] args) {
        // 主启动类
        SpringApplication.run(Main80.class,args);
    }

}