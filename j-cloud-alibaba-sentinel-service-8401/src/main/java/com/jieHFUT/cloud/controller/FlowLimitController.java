package com.jieHFUT.cloud.controller;

import com.jieHFUT.cloud.service.FlowLimitService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA() {
        return "------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "------testB";
    }

    /**
     * 流控-链路演示demo
     * C和 D两个请求都访问 flowLimitService.common()方法，阈值到达后对C限流，对 D不管
     */
    @Resource
    private FlowLimitService flowLimitService;

    @GetMapping("/testC")
    public String testC() {
        flowLimitService.common();
        return "------testC";
    }
    @GetMapping("/testD")
    public String testD() {
        flowLimitService.common();
        return "------testD";
    }
}


