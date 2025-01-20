package com.jiehfut.cloud.controller;


import cn.hutool.core.util.IdUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 为断路器提供的一个请求接口（服务熔断类）
 * 1.新建该控制方法
 * 2.断路器的 feign 接口：PayFeignApi
 * 3.断路器在消费者（客户端）配置
 *   （1）
 *   （2）改 pom
 *   （3）写 yml
 */
@RestController
public class PayCircuitController {

    //========= Resilience4j CircuitBreaker （暴露器-服务熔断-按照次数）的例子
    @GetMapping(value = "/pay/circuit/{id}")
    public String myCircuit(@PathVariable("id") Integer id) {
        if(id == -4) throw new RuntimeException("----circuit id 不能负数");
        if(id == 9999) {
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        return "Hello, circuit! inputId:  "+ id + "\t" + IdUtil.simpleUUID();
    }

    //=========Resilience bulkhead 的例子（隔离机制-信号量仓壁）
    @GetMapping(value = "/pay/bulkhead/{id}")
    public String myBulkhead(@PathVariable("id") Integer id) {
        if(id == -4) throw new RuntimeException("----bulkhead id 不能-4");

        if(id == 9999) {
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        return "Hello, bulkhead! inputId:  "+ id + "\t" + IdUtil.simpleUUID();
    }

    //=========Resilience4j ratelimit 的例子（限流的办法）
    @GetMapping(value = "/pay/ratelimit/{id}")
    public String myRatelimit(@PathVariable("id") Integer id)
    {
        return "Hello, myRatelimit欢迎到来 inputId:  "+id+" \t " + IdUtil.simpleUUID();
    }


}