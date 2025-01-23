package com.jieHFUT.cloud.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @SentinelResource 声明哨兵要保护的资源
 *
 */
@RestController
@Slf4j
public class RateLimitController {


    @GetMapping("/rateLimit/byUrl")
    public String byUrl() {
        return "按rest地址限流测试OK";
    }






    @GetMapping("/rateLimit/byResource")
    @SentinelResource(value = "byResource-SentinelResource", blockHandler = "handlerBlockHandler") // value 默认 ""
    public String byResource() {
        return "按照资源名称 SentinelResource 限流测试OK";
    }
    // 如果正常就走上面这个逻辑，如果异常就走下面的这个逻辑，相当于兜底处理
    public String handlerBlockHandler(BlockException ex) {
        return "服务不可用，触发了 @SentinelResource 启动";
    }





    @GetMapping("/rateLimit/doAction/{p1}")
    @SentinelResource(value = "doActionSentinelResource",
            blockHandler = "doActionBlockHandler", fallback = "doActionFallback")
    public String doAction(@PathVariable("p1") Integer p1) {
        if (p1 == 0){
            throw new RuntimeException("p1等于零直接异常");
        }
        return "doAction";
    }
    // 如果正常就走上面这个逻辑，如果异常就走下面的这个逻辑，相当于兜底处理
    public String doActionBlockHandler(@PathVariable("p1") Integer p1,BlockException e){
        log.error("sentinel配置自定义限流了:{}", e);
        return "sentinel配置自定义限流了";
    }

    public String doActionFallback(@PathVariable("p1") Integer p1,Throwable e){
        log.error("程序逻辑异常了:{}", e);
        return "程序逻辑异常了"+"\t"+e.getMessage();
    }


    /**
     * 热点参数限流
     * @param p1
     * @param p2
     * @return
     */
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "dealHandler_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false) String p1,
                             @RequestParam(value = "p2",required = false) String p2){
        return "------testHotKey";
    }
    // 如果没有发生请求限流就走上面的响应，否则就走下面这个响应
    // 只对 P1 进行参数限流，如果请求携带参数 P1，就直接限流
    // 如果请求中仅仅携带 P2，不会限流走下面这个方法
    public String dealHandler_testHotKey(String p1,String p2,BlockException exception) {
        return "-----dealHandler_testHotKey";
    }










}


