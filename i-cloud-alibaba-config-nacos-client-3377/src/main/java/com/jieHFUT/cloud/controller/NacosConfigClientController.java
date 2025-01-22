package com.jieHFUT.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshScope //在控制器类加入 @RefreshScope 注解使当前类下的配置支持 Nacos 的动态刷新功能（动态广播通知）。
public class NacosConfigClientController {

    // 动态获取服务中心 nacos 的配置信息
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/config/info")
    public String getConfigInfo() {
        return configInfo;
    }

}
