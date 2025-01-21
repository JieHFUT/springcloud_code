package com.jiehfut.cloud;

import java.time.ZonedDateTime;

public class GetPredicateData {
    public static void main(String[] args) {

        /**
         * 获取形如 2025-01-21T11:02:43.305109100+08:00[Asia/Shanghai] 格式的时间
         * 在网关的断言中，一般需要在抢购，定时等行为需要使用该断言
         * 表示在该时间以后网关才能放行请求
         */
        ZonedDateTime dateTime = ZonedDateTime.now();
        System.out.println(dateTime);
    }
    /**
     * spring:
     *   cloud:
     *     gateway:
     *       # 网关的核心之一 => route（路由） - 进行路径匹配
     *       routes:
     *         - id: pay_routh1 # pay_routh1                 # 路由的ID(类似mysql主键ID)，没有固定规则但要求唯一，建议配合服务名
     *           # uri: http://localhost:8001                # 匹配后提供服务的路由地址（问题是出现路径写死问题，一般不止一个微服务提供者）
     *           uri: lb://cloud-payment-service
     *           predicates:
     *             - Path=/pay/gateway/get/**                # 断言，路径相匹配的进行路由（true 可以访问）
     *             # 网关的核心之二（1） => 通过 predicate 进行 After-Route-Predicate 断言（表示在该时间之后网关才会放行请求）
     *             - After=2025-01-21T11:06:43.305109100+08:00[Asia/Shanghai]
     *             # 网关的核心之二（2） => 通过 predicate 进行 Bofore-Route-Predicate 断言（表示在该时间之前网关才会放行请求）
     *             - Before=2025-01-21T11:06:43.305109100+08:00[Asia/Shanghai]
     *             # 网关的核心之二（3） => 通过 predicate 进行 Between-Route-Predicate 断言（表示在该时间之内网关才会放行请求）
     *             - Between=2025-01-21T11:06:43.305109100+08:00[Asia/Shanghai],2025-02-21T11:06:43.305109100+08:00[Asia/Shanghai]
     *             # 网关的核心之二（4） => 通过 predicate 进行 Cookie-Route-Predicate 断言（表示请求携带该 cookie 网关才会放行请求）
     *             # 可以在 cmd 中使用：curl http://localhost:9527/pay/gateway/get/1
     *             # 可以在 cmd 中使用：curl http://localhost:9527/pay/gateway/get/1 --cookie "username=jieHFUT"
     *             - Cookie=username,jieHFUT
     *             # 网关的核心之二（5） => 通过 predicate 进行 Header-Route-Predicate 断言（表示请求头中根据正则表达式匹配成功网关才会放行请求）
     *             # 可以在 cmd 中使用：curl http://localhost:9527/pay/gateway/get/1 -H "X-Request-Id=123456"
     *             - Header=X-Request-Id, \d+
     *             # 网关的核心之二（6） => 通过 predicate 进行 Host-Route-Predicate 断言（表示请求头中根据主机域名匹配成功网关才会放行请求）
     *             # 可以在 cmd 中使用：curl http://localhost:9527/pay/gateway/get/1 -H "Host:www.jieHFUT.cloud"
     *             - Host=**.baidu.com,**.jieHFUT.cloud
     *             # 网关的核心之二（7） => 通过 predicate 进行 Path-Route-Predicate 断言（表示请求路径匹配成功网关才会放行请求）
     *             - Path=/pay/gateway/get/**
     *             # 网关的核心之二（8） => 通过 predicate 进行 Query-Route-Predicate 断言（表示请求需要携带该参数（参数可以为正则）网关才会放行请求）
     *             - Query=colour, green
     *             # 网关的核心之二（9） => 通过 predicate 进行 RemoteAddr-Route-Predicate 断言（表示请求需要是该网络号内，网关才会放行请求）
     *             - RemoteAddr=192.168.124.1/24 # 外部访问我的 IP 限制，最大跨度不超过32，目前是1~24它们是 CIDR 表示法。
     *             # 网关的核心之二（10） => 通过 predicate 进行 Method-Route-Predicate 断言（表示请求需要是该方法，网关才会放行请求）
     *             - Method=GET,POST # 限制请求方法
     *
     *
     */


}
