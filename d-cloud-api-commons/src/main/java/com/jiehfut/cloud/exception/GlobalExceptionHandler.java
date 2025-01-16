package com.jiehfut.cloud.exception;


import com.jiehfut.cloud.resp.ResultData;
import com.jiehfut.cloud.resp.ReturnCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 统一的全局异常处理器
 * 异常直接响应服务器内部异常
 *
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class) // 捕捉运行时异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 说明是后台服务器异常
    public ResultData<String> exceptionHandler(Exception e) {
        System.out.println("#### 进入全局异常处理 ####");
        log.error("全局异常处理，处理信息是：" + e.getMessage());

        return ResultData.fail(ReturnCodeEnum.RC500);
    }
}
