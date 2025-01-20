package com.jiehfut.cloud.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.jiehfut.cloud.apis.PayFeignApi;
import com.jiehfut.cloud.entities.PayDTO;
import com.jiehfut.cloud.resp.ResultData;
import com.jiehfut.cloud.resp.ReturnCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * post   - pay/add      - Pay
 * delete - pay/del{id}  -
 * put    - pay/update   - PayTDO
 * get    - pay/get/{id} -
 * get    - pay/get      -
 */

@RestController
public class OrderController {

    /**
     * 消费者
     * 这个地方是使用 feign 接口时具体实现调用请求的地方
     *
     *     @PostMapping("/pay/add")
     *     public ResultData<String> addPay(@RequestBody PayDTO payDTO);
     *
     *     @DeleteMapping("/pay/del/{id}")
     *     public ResultData<String> deletePay(@PathVariable("id") Integer id);
     *
     *     @PutMapping("/pay/update")
     *     public ResultData<String> updatePay(@RequestBody PayDTO payDTO);
     *
     *     @GetMapping("/consumer/pay/get/{id}")
     *     public ResultData getPayInfo(@PathVariable("id") Integer id);
     *
     *     @GetMapping("/pay/get")
     *     public ResultData<List<PayDTO>> getPayList();
     *
     *     @GetMapping("/pay/get/info")
     *     public String getInfoByConsul();
     */

    @Resource
    private PayFeignApi payFeignApi;


    @PostMapping("/feign/pay/add")
    public ResultData<String> addPay(@RequestBody PayDTO payDTO) {
        System.out.println("第一步：模拟本地 addOrder 新增订单成功");
        System.out.println("第二步：开启 addPay 支付服务远程调用");
        return payFeignApi.addPay(payDTO);
    }


    @DeleteMapping("/feign/pay/del/{id}")
    public ResultData<String> deletePay(@PathVariable("id") Integer id){
        return payFeignApi.deletePay(id);
    }

    @PutMapping("/feign/pay/update")
    public ResultData<String> updatePay(@RequestBody PayDTO payDTO){
        return payFeignApi.updatePay(payDTO);
    }

    @GetMapping("/feign/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){
        System.out.println("支付宝微服务远程调用，按照ID查询订单支付流水信息");
        ResultData payInfo = null;
        try {
            System.out.println("通过id获取流水信息调用开始：" + DateUtil.now());
            payInfo = payFeignApi.getPayInfo(id);
        } catch ( Exception e ) {
            e.printStackTrace();
            System.out.println("通过id获取流水信息调用结束：" + DateUtil.now());
            /**
             * 测试结果显示默认的超时时间是 60 s => 可以自己进行设置客户端 openfeign 超时时间 => .yml
             * 1.全局配置：某一个消费者调用其他微服务（如支付，用户，订单）全部都是默认超时多少
             * 2.指定配置：某一个消费者调用每一个微服务分别设置超时时间
             */
            ResultData.fail(ReturnCodeEnum.RC500);
        }
        return payInfo;
    }

    @GetMapping("/feign/pay/get")
    public ResultData<List<PayDTO>> getPayList(){
        return payFeignApi.getPayList();
    }


    /**
     * openfeign 天然支持负载均衡（默认轮询）
     * @param
     * @return
     */
    @GetMapping("/feign/pay/get/info")
    public String getInfoByConsul(){
        return payFeignApi.getInfoByConsul();
    }


}

