package com.jiehfut.cloud.controller;


import com.jiehfut.cloud.entities.Pay;
import com.jiehfut.cloud.entities.PayDTO;
import com.jiehfut.cloud.resp.ResultData;
import com.jiehfut.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "支付微服务模块", description = "支付 CRUD")
public class PayCntroller {

    @Resource
    private PayService payService;

    /**
     {
         "payNo":"payNo~",
         "orderNo":"orderNo~",
         "userId":"1",
         "amount":"9.99"
     }
     * @param pay
     * @return
     */
    @PostMapping("/pay/add")
    @Operation(summary = "新增", description = "新增一条流水记录，JSON 串参数")
    public ResultData<String> addPay(@RequestBody Pay pay) {
        System.out.println(pay.toString());

        int add = payService.add(pay);
        return add == 1 ? ResultData.success("插入成功") : ResultData.fail("-1", "插入失败");
    }

    @DeleteMapping("/pay/del/{id}")
    @Operation(summary = "删除", description = "根据主键 ID 删除流水记录")
    public ResultData<String> deletePay(@PathVariable("id") Integer id) {
        int delete = payService.delete(id);
        return delete == 1 ? ResultData.success("删除成功") : ResultData.fail("-1", "删除失败");
    }

    @PutMapping("/pay/update")
    @Operation(summary = "更新", description = "更新流水记录")
    public ResultData<String> updatePay(@RequestBody PayDTO payDTO) {
        Pay pay = new Pay();
        // BeanUtils 进行对拷
        BeanUtils.copyProperties(payDTO, pay);
        int update = payService.update(pay);
        return update == 1 ? ResultData.success("更新成功") : ResultData.fail("-1", "更新失败");
    }

    @GetMapping("/pay/get/{id}")
    @Operation(summary = "根据主键 ID 来获取一条流水记录")
    public ResultData<Pay> getPayById(@PathVariable("id") Integer id) {
        Pay pay = payService.findById(id);
        return ResultData.success(pay);
    }

    @GetMapping("/pay/get")
    @Operation(summary = "获取全部流水", description = "获取全部流水记录")
    public ResultData<List<Pay>> getPayList() {
        List<Pay> allPays = payService.findAll();
        return ResultData.success(allPays);
    }


    /**
     * 测试 8001 能不能从 consul 服务器中读取到配置的相关信息
     * 分布式配置
     * 1.在该微服务 pom.xml 中引入支持
     *   spring-cloud-starter-consul-config
     *   spring-cloud-starter-bootstrap
     * 2.新增配置文件 bootstrap.yml，整理 application.yml 配置
     * 3.本地在 consul 服务中进行 key-value 数值填写（包括文件夹）
     * 4.测试从 consul 中读取配置信息（config/cloud-payment-service/data）
     *   data 中是一个 yaml 文件，jiehfut.info
     */
    @Value("${server.port}")
    private String port;

    @GetMapping("/pay/get/info")
    public String getInfoByConsul(@Value("${jiehfut.info}") String jiehfutInfo) {
        return "jiehfutInfo = " + jiehfutInfo + "; port = " + port;
    }

    /**
     * 1.设置分布式配置
     * 2.实现分布式配置的动态刷新
     * 3.如何做到 consul 服务关闭后重启仍然保存配置的 key-value 键值对
     *   consul 的配置持久化 ?
     *
     */


}
