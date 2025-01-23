package com.jieHFUT.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Empower授权规则，用来处理请求的来源（黑白名单）
 * 授权规则中，调用方法的调用方信息需要通过 ContextUtil.enter(resourceName, origin) 中的 origin 传入
 * 所以需要添加依赖
 *
 */
@RestController
@Slf4j
public class EmpowerController {

    @GetMapping(value = "/empower")
    public String requestSentinel4(){
        log.info("测试 Sentinel 授权规则 empower");
        return "Sentinel 授权规则";
    }

}