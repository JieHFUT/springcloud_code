package com.jiehfut.cloud.service;

import com.jiehfut.cloud.entities.Pay;

import java.util.List;


public interface PayService {

    /**
     * 添加一条支付记录
     * @param pay
     * @return
     */
    public int add(Pay pay);

    /**
     * 根据主键删除某一条支付记录
     * @param id
     * @return
     */
    public int delete(Integer id);

    /**
     * 更新某一条支付记录
     * @param pay
     * @return
     */
    public int update(Pay pay);

    /**
     * 按照主键进行查询
     * @param id
     * @return
     */
    public Pay findById(Integer id);

    /**
     * 全部查询
     * @return
     */
    public List<Pay> findAll();
}
