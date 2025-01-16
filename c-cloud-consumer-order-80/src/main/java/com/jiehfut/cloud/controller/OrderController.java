package com.jiehfut.cloud.controller;

import com.jiehfut.cloud.entities.PayDTO;
import com.jiehfut.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * post   - pay/add      - Pay
 * delete - pay/del{id}  -
 * put    - pay/update   - PayTDO
 * get    - pay/get/{id} -
 * get    - pay/get      -
 */

@RestController
public class OrderController {

    public static final String PaymentSrv_URL = "http://localhost:8001"; //先写死，硬编码

    @Resource
    private RestTemplate restTemplate;

    /**
     * 一般情况下，通过浏览器的地址栏输入url，发送的只能是 get 请求
     * 我们底层调用的是 post方法，模拟消费者发送 get 请求，客户端消费者
     * 参数可以不添加 @RequestBody
     * @param payDTO
     * @return
     */
    @GetMapping("/consumer/pay/add")
    public ResultData addOrder(PayDTO payDTO){
        return restTemplate.postForObject(PaymentSrv_URL + "/pay/add", payDTO, ResultData.class);
    }

    /**
     * 根据主键 ID 来删除某一条流水记录
     * @param id
     * @return
     */
    @GetMapping("/consumer/pay/del/{id}")
    public ResultData deletePay(Integer id){
        return restTemplate.postForObject(PaymentSrv_URL + "/pay/del/" + id, id, ResultData.class);
    }

    /**
     * 更新某一条流水记录
     * @param payDTO
     * @return
     */
    @GetMapping("/consumer/pay/update")
    public ResultData updatePay(PayDTO payDTO){
        return restTemplate.postForObject(PaymentSrv_URL + "/pay/update", payDTO, ResultData.class);
    }

    /**
     * 按照主键 ID 来获取一条流水记录
     * @param id
     * @return
     */
    @GetMapping("/consumer/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/"+id, ResultData.class, id);
    }

    /**
     * 获取全部流水记录列表
     * @return
     */
    @GetMapping("/consumer/pay/get")
    public ResultData getPayInfoList() {
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get", ResultData.class);
    }




}

