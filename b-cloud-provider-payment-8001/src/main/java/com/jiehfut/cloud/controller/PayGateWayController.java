package com.jiehfut.cloud.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.jiehfut.cloud.entities.Pay;
import com.jiehfut.cloud.resp.ResultData;
import com.jiehfut.cloud.service.PayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;


/**
 * 网关 GateWay
 *
 */
@RestController
public class PayGateWayController {
    @Resource
    PayService payService;

    @GetMapping(value = "/pay/gateway/get/{id}")
    public ResultData<Pay> getById(@PathVariable("id") Integer id) {
        Pay pay = payService.findById(id);
        return ResultData.success(pay);
    }

    @GetMapping(value = "/pay/gateway/info")
    public ResultData<String> getGatewayInfo() {
        return ResultData.success("gateway info test："+ IdUtil.simpleUUID());
    }


    /**
     * 测试在使用网关的过程中，对请求转发到控制器方法的前后的处理 = filter
     * 如果请求头中有 X-Request-jieHFUT1 || X-Request-jieHFUT2 就将其显示请求头中的参数
     * @param request
     * @return
     */
    @GetMapping(value = "/pay/gateway/filter")
    public ResultData<String> getGatewayFilter(HttpServletRequest request) {
        String result = "";
        Enumeration<String> headers = request.getHeaderNames();
        while(headers.hasMoreElements()) {
            String headName = headers.nextElement();
            String headValue = request.getHeader(headName);
            System.out.println("请求头名: " + headName +"\t\t\t"+"请求头值: " + headValue);
            if(headName.equalsIgnoreCase("X-Request-jieHFUT1")
                    || headName.equalsIgnoreCase("X-Request-jieHFUT2")) {
                result = result+headName + "\t " + headValue + " ";
            }
        }
        System.out.println("--------------------------------------------");
        String customerId = request.getParameter("customerId");
        System.out.println("request Parameter customerId: " + customerId);

        String customerName = request.getParameter("customerName");
        System.out.println("request Parameter customerName: " + customerName);
        System.out.println("=============================================");
        return ResultData.success("getGatewayFilter 过滤器 test： "+result+" \t "+ DateUtil.now());
    }
}
