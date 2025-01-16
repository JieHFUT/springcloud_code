package com.jiehfut.cloud.resp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 响应的统一封装格式
 * 状态码
 * 状态码解释
 * 传输数据
 * 响应时间戳
 *
 * @param <T>
 */
@Data
@Accessors(chain = true) // 开启链式编程：设置chain=true时，setter方法返回的是this（也就是对象自己）
                         // 代替了默认的返回值void，直接链式操作对象（如 user.setName("aaa").setAge(18).set().set()）
public class ResultData<T> {

    private String code;/** 结果状态 ,具体状态码参见枚举类 ReturnCodeEnum.java*/
    private String message;
    private T data;
    private long timestamp;

    public ResultData (){
        this.timestamp = System.currentTimeMillis();
    }


    /**
     * 成功响应
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(ReturnCodeEnum.RC200.getCode());
        resultData.setMessage(ReturnCodeEnum.RC200.getMessage());
        resultData.setData(data);
        return resultData;
    }


    /**
     *
     * @param code 后端处理失败的状态吗
     * @param message 处理失败的信息
     * @return data 为空的一个统一封装响应对象
     * @param <T>
     */
    public static <T> ResultData<T> fail(String code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(code);
        resultData.setMessage(message);
        return resultData;
    }


    /**
     * 相应后端处理失败的情况
     * @param returnCodeEnum 枚举类型
     * @return data 为空的一个统一封装响应对象
     * @param <T>
     */
    public static <T> ResultData<T> fail(ReturnCodeEnum returnCodeEnum) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(returnCodeEnum.getCode());
        resultData.setMessage(returnCodeEnum.getMessage());
        return resultData;
    }

}
