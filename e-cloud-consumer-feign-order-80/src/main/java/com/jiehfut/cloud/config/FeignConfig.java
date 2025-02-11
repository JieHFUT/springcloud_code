package com.jiehfut.cloud.config;

import feign.Logger;
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
    // 修改 Retryer 内属性实现重试
    @Bean
    public Retryer myRetryer() {

        // return Retryer.NEVER_RETRY; // Feign 默认配置是不走重试策略的

        // 最大请求次数为 3(1+2)，初始间隔时间为 100ms，即100ms以后启动这个配置，重试间最大间隔时间为 1s
        return new Retryer.Default(100,1,3);
    }

    /**
     * 对请求和响应进行 GZIP压缩 - yml
     * Spring Cloud OpenFeign支持对请求和响应进行 GZIP压缩，以减少通信过程中的性能损耗。
     * 通过下面的两个参数设置，就能开启请求与相应的压缩功能：
     * spring.cloud.openfeign.compression.request.enabled=true
     * spring.cloud.openfeign.compression.response.enabled=true
     *
     * 细粒度化设置
     * 对请求压缩做一些更细致的设置，比如下面的配置内容指定压缩的请求数据类型并设置了请求压缩的大小下限，
     * 只有超过这个大小的请求才会进行压缩：
     * spring.cloud.openfeign.compression.request.enabled=true
     * spring.cloud.openfeign.compression.request.mime-types=text/xml,application/xml,application/json #触发压缩数据类型
     * spring.cloud.openfeign.compression.request.min-request-size=2048 #最小触发压缩的大小
     *
     */


    /**
     * Feign 提供了日志打印功能，我们可以通过配置来调整日志级别 - yml
     * 从而了解 Feign 中 Http 请求的细节，
     * 说白了就是对 Feign接口的调用情况进行监控和输出
     * public enum Level {
     *     NONE, - No logging.
     *     BASIC, - Log only the request method and URL and the response status code and execution time.
     *     HEADERS, - Log the basic information along with request and response headers.
     *     FULL - Log the headers, body, and metadata for both requests and responses.
     * }
     *
     *
     *
     * 1.配置日志 bean 组件
     * 2.在 .yml 中配置开启 feign 日志的客户端
     * 3.后台日志查看
     *
     *   （1）触发请求响应的压缩功能：
     *     ---> GET http://cloud-payment-service/pay/get/6 HTTP/1.1
     *     Accept-Encoding: gzip
     *     Accept-Encoding: deflate
     *     ---> END HTTP (0-byte body)
     *     <--- ERROR SocketTimeoutException: Read timed out (4254ms)
     *     ava.net.SocketTimeoutException: Read timed out
     *   （2）看到三次请求，openfeign 的重试请求
     *
     *
     */





    // 配置日志 bean 组件

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 残留问题：如果某一个微服务发生异常不能响应了，如何表明？
     * 也就是如何实现服务的降级？
     */
}



