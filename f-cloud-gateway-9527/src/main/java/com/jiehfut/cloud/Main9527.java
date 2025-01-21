package com.jiehfut.cloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关 GateWay
 */
@SpringBootApplication
@EnableDiscoveryClient // 服务注册和发现
public class Main9527 {

    public static void main(String[] args) {
        SpringApplication.run(Main9527.class, args);

        /**
         * 网关配置流程
         * 1.建立 moudle - 本工程
         * 2.改 pom - spring-cloud-starter-gateway
         * 3.写 yaml - 端口 & 服务注册
         * 4.主启动
         * 5.业务撰写
         *    8001：写 controller 方法
         *    9527：配置 yaml 路由匹配配置
         * 6.添加 80 -> 9527 -> 8001 的 feign 接口调用
         *    80：修改 feign 接口（commons-PayFeignApi-添加接口）
         *    80：新建 controller 方法
         *    开启网关：发现可以由 80 访问
         *    关闭网关：发现还是可以由 80 访问
         *    => 总结：系统内自己调用不需要走网关
         *            外部调用需要走网关
         * 7.要想让网关生效：需要在暴露接口中声明哪些需要走网关
         *     开启网关：80 访问接口正常
         *     关闭网关：80 访问接口异常
         * 8.9527 配置地址写死问题等：高级特性
         */


        /**
         * 9527 在启动的时候会默认加载出厂路由
         * gateway 里面 predicate 工厂会与 http 请求的不同属性匹配，多个 route predicate 工厂可以进行组合
         *
         * 2025-01-21T10:18:35.279+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [After]
         * 2025-01-21T10:18:35.279+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [Before]
         * 2025-01-21T10:18:35.280+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [Between]
         * 2025-01-21T10:18:35.280+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [Cookie]
         * 2025-01-21T10:18:35.280+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [Header]
         * 2025-01-21T10:18:35.280+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [Host]
         * 2025-01-21T10:18:35.280+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [Method]
         * 2025-01-21T10:18:35.280+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [Path]
         * 2025-01-21T10:18:35.280+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [Query]
         * 2025-01-21T10:18:35.280+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [ReadBody]
         * 2025-01-21T10:18:35.280+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [RemoteAddr]
         * 2025-01-21T10:18:35.280+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [XForwardedRemoteAddr]
         * 2025-01-21T10:18:35.280+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [Weight]
         * 2025-01-21T10:18:35.280+08:00  INFO 20192 --- [cloud-gateway] [main] o.s.c.g.r.RouteDefinitionRouteLocator : Loaded RoutePredicateFactory [CloudFoundryRouteService]
         *
         * predicate 有两种配置方法
         * 10 大断言配置：https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/request-predicates-factories.html
         *    1.快捷配置（过滤器组成）
                 spring:
                    cloud:
                        gateway:
                            routes:
                            - id: after_route
                                uri: https://example.org
                                predicates:
                                - Cookie=mycookie,mycookievalue  # mycookie 匹配 mycookievalue
              2.详情配置（全称形式）
                 spring:
                     cloud:
                         gateway:
                             routes:
                             - id: after_route
                                 uri: https://example.org
                                 predicates:
                                 - name: Cookie # 类似键值对（key 为 name，value 为 map）
                                     args:
                                         name: mycookie
                                         regexp: mycookievalue
         */



    }
}
