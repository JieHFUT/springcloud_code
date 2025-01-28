package com.jiehfut.cloud.apis;


import com.jiehfut.cloud.entities.PayDTO;
import com.jiehfut.cloud.resp.ResultData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 8001 匹配的 feign 接口，其他消费者想要调用 8001服务，只能调用这个接口里面有的接口
 * 这个接口里面的方法必需保存和服务提供者（支付的 feign 接口）
 *
 * 1.方法签名一致
 * 2.路径一致
 */
// @FeignClient(value = "cloud-payment-service") // 声明这是哪一个微服务的 feign 接口
                                                 // 自己人内部，自己访问自己，写微服务名字OK，外部调用需要调用网关
@FeignClient(value = "cloud-gateway") // 暴露的接口被调用后，会调用网关进行处理
public interface PayFeignApi {

    @PostMapping("/pay/add")
    public ResultData<String> addPay(@RequestBody PayDTO payDTO);

    @DeleteMapping("/pay/del/{id}")
    public ResultData<String> deletePay(@PathVariable("id") Integer id);

    @PutMapping("/pay/update")
    public ResultData<String> updatePay(@RequestBody PayDTO payDTO);

    @GetMapping("/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id);

    @GetMapping("/pay/get")
    public ResultData<List<PayDTO>> getPayList();

    @GetMapping("/pay/get/info")
    public String getInfoByConsul();

    /**
     * 使用断路器的 feign 接口
     * Resilience4j CircuitBreaker 的例子
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/circuit/{id}")
    public String myCircuit(@PathVariable("id") Integer id);

    /**
     * Resilience4j Bulkhead 的例子（隔离机制-信号量仓壁）
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/bulkhead/{id}")
    public String myBulkhead(@PathVariable("id") Integer id);

    /**
     * Resilience4j Ratelimit 的例子（限流的办法）
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/ratelimit/{id}")
    public String myRatelimit(@PathVariable("id") Integer id);


    /**
     * Micrometer(Sleuth)进行链路监控的例子
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/micrometer/{id}")
    public String myMicrometer(@PathVariable("id") Integer id);


    /**
     * GateWay进行网关测试案例01
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/gateway/get/{id}")
    public ResultData getById(@PathVariable("id") Integer id);


    /**
     * GateWay进行网关测试案例02
     * @return
     */
    @GetMapping(value = "/pay/gateway/info")
    public ResultData<String> getGatewayInfo();
}
