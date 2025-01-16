package com.jiehfut.cloud.service.impl;

import com.jiehfut.cloud.entities.Pay;
import com.jiehfut.cloud.mapper.PayMapper;
import com.jiehfut.cloud.service.PayService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceImpl implements PayService {

    @Resource
    private PayMapper payMapper;

    @Override
    public int add(Pay pay) {
        // insertSelective：null 的属性不会新增，使用数据库的默认值
        // insert：null 的属性会新增，不使用数据库的默认值
        return payMapper.insertSelective(pay);
    }

    @Override
    public int delete(Integer id) {
        // 按照主键进行删除
        return payMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Pay pay) {
        // 按照主键更新不为 null 的值
        return payMapper.updateByPrimaryKeySelective(pay);
    }

    @Override
    public Pay findById(Integer id) {
        // 根据主键来查询结果
        return payMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Pay> findAll() {
        // 选择全部记录
        return payMapper.selectAll();
    }
}
