package com.jiehfut.cloud.controller;

import com.jiehfut.cloud.apis.PayFeignApi;
import com.jiehfut.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * 测试 9527 网关的控制层方法
 * 80 消费者先调用 feign 实现负载均衡等，再到 9527 实现网关，最后请求转发到 8001 微服务
 * 80 - feign - 9527:gateway - 8001
 * 
 */
@RestController
public class OrderGateWayController {
    @Resource
    private PayFeignApi payFeignApi;

    @GetMapping(value = "/feign/pay/gateway/get/{id}")
    public ResultData getById(@PathVariable("id") Integer id) {
        return payFeignApi.getById(id);
    }

    @GetMapping(value = "/feign/pay/gateway/info")
    public ResultData<String> getGatewayInfo() {
        return payFeignApi.getGatewayInfo();
    }
}

