package com.jiehfut.cloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 给前端交互的数据传输对象
 * 只能暴露 DTO，不能暴露表结构
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayDTO implements Serializable {

    private Integer id;
    //支付流水号
    private String payNo;
    //订单流水号
    private String orderNo;
    //用户账号ID
    private Integer userId;
    //交易金额
    private BigDecimal amount;

}

