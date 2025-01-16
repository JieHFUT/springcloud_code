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
public class PayDTO implements Serializable
{
    private Integer id;
    //支付流水号
    private String payNo;
    //订单流水号
    private String orderNo;
    //用户账号ID
    private Integer userId;
    //交易金额
    private BigDecimal amount;

    public PayDTO() {
    }

    public PayDTO(Integer id, Integer userId, String orderNo, String payNo, BigDecimal amount) {
        this.id = id;
        this.userId = userId;
        this.orderNo = orderNo;
        this.payNo = payNo;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PayDTO{" +
                "id=" + id +
                ", payNo='" + payNo + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", userId=" + userId +
                ", amount=" + amount +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }
}

