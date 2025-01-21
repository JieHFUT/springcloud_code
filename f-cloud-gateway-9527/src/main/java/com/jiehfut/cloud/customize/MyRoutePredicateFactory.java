package com.jiehfut.cloud.customize;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * 自己在网关中定义一个请求资源匹配（该工厂名字叫 My）
 * 说明：定义一个可以配置的请求等级 - requestRank
 *      按照 Gold, silver, copper 进行等级划分
 *      根据请求中携带的参数来判断请求是否被允许
 *
 * config 类是我们的路由断言规则（等级需要在 yml 中去配置）
 *
 */
@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {


    public MyRoutePredicateFactory() {
        super(MyRoutePredicateFactory.Config.class);
    }

    /**
     * 设置支持短格式
     * @return
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("requestRank");
    }

    @Override
    public Predicate<ServerWebExchange> apply(MyRoutePredicateFactory.Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                // 对 ServerWebExchange 进行判断匹配（只有匹配成功放行）
                // http://localhost:9527/pay/gateway/get/1?requestRank=...

                String requestRank = serverWebExchange.getRequest().getQueryParams().getFirst("requestRank");
                if (requestRank == null) {
                    return false;
                } else if (requestRank.equalsIgnoreCase(config.getRequestRank())) {
                    return true;
                } else {
                    return false;
                }
            }

        };
    }


    /**
     * Config 类
     * 是我们自定义的用于对路由进行判断的规则
     */
    @Validated
    public static class Config {
        @Setter
        @Getter
        @NotEmpty
        private String requestRank;

    }


}
