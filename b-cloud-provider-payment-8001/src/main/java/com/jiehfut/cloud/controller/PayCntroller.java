package com.jiehfut.cloud.controller;


import com.jiehfut.cloud.entities.Pay;
import com.jiehfut.cloud.entities.PayDTO;
import com.jiehfut.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    public String addPay(@RequestBody Pay pay) {
        System.out.println(pay.toString());

        int add = payService.add(pay);
        return add == 1 ? "成功插入" : "插入失败";
    }

    @DeleteMapping("/pay/del/{id}")
    @Operation(summary = "删除", description = "根据主键 ID 删除流水记录")
    public Integer deletePay(@PathVariable("id") Integer id) {
        return payService.delete(id);
    }

    @PutMapping("/pay/update")
    @Operation(summary = "更新", description = "更新流水记录")
    public String updatePay(@RequestBody PayDTO payDTO) {
        Pay pay = new Pay();
        // BeanUtils 进行对拷
        BeanUtils.copyProperties(payDTO, pay);
        int update = payService.update(pay);
        return update == 1 ? "更新成功" : "更新失败";
    }

    @GetMapping("/pay/get/{id}")
    @Operation(summary = "根据主键 ID 来获取一条流水记录")
    public Pay getPayById(@PathVariable("id") Integer id) {
        Pay pay = payService.findById(id);
        return pay;
    }

    @GetMapping("/pay/get")
    @Operation(summary = "获取全部流水", description = "获取全部流水记录")
    public List<Pay> getPayList() {
        return payService.findAll();
    }

}
