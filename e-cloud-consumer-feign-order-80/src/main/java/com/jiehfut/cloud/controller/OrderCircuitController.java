package com.jiehfut.cloud.controller;



import com.jiehfut.cloud.apis.PayFeignApi;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试断路器的控制层 - 兜底
 * Resilience4j CircuitBreaker 的例子
 * 在 PayFeignApi 接口类中已经有下面接口
 */

@RestController
public class OrderCircuitController {
    @Resource
    private PayFeignApi payFeignApi;

    @GetMapping(value = "/feign/pay/circuit/{id}")
    // 声明（断路器）要调用哪个微服务（在 yml 中配置的启动熔断器的微服务），如果产生了服务异常，给一个兜底的服务降级的方法（服务）
    @CircuitBreaker(name = "cloud-payment-service", fallbackMethod = "myCircuitFallback")
    public String myCircuitBreaker(@PathVariable("id") Integer id) {
        // 该微服务正常调用走接口调用服务
        return payFeignApi.myCircuit(id);
    }
    // 异常情况兜底：myCircuitFallback就是服务降级后的兜底处理方法
    public String myCircuitFallback(Integer id, Throwable t) {
        // 这里是容错处理逻辑，返回备用结果
        return "myCircuitFallback，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~";
    }
}