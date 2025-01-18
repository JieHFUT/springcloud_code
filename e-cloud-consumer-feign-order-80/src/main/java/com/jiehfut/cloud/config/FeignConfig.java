package com.jiehfut.cloud.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 开始 openfeign 的重试机制
 * openfeign 用重试机制，但是默认是不开启的
 * 我们可以通过新增配置类，修改 Retryer 类的参数来配置重试机制
 * Retryer 里面的 default 是 feign 的默认配置
 *
 * 开启重试机制就是如果请求失败（例如超时），会接着请求，到达设置的最大请求次数
 * 不开启重试机制就是请求失败，直接报错，不会再次请求
 * （例如超时设置 4 秒，重试设置 3 次）=>（如果开始，会一次请求三次，三次都没有请求到才会报错，不开启请求一次就报错）
 */
@Configuration
public class FeignConfig {
    @Bean
    public Retryer myRetryer() {

        // return Retryer.NEVER_RETRY; // Feign 默认配置是不走重试策略的

        // 最大请求次数为 3(1+2)，初始间隔时间为 100ms，即100ms以后启动这个配置，重试间最大间隔时间为 1s
        return new Retryer.Default(100,1,3);
    }
}
