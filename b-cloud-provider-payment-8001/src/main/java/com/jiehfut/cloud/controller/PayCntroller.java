package com.jiehfut.cloud.controller;


import com.jiehfut.cloud.entities.Pay;
import com.jiehfut.cloud.service.PayService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PayCntroller {

    @Resource
    private PayService payService;


    @RequestMapping("/pay/add")
    public String addPay(@RequestBody Pay pay) {
        System.out.println(pay.toString());

        int add = payService.add(pay);
        return add == 1 ? "成功插入" : "插入失败";
    }

    @RequestMapping("/pay/del/{id}")
    public Integer deletePay(@PathVariable("id") Integer id) {
        return payService.delete(id);
    }



}
