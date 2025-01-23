package com.jieHFUT.cloud.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;


/**
 * 这里是去配置黑白名单（授权规则）的调用者的信息入口
 * 来源的处理器转换，由这个来设置白名单还是黑名单
 */
@Component
public class MyRequestOriginParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {

        return httpServletRequest.getParameter("serverName");
    }

}