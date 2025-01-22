package com.jiehfut.cloud.customize;


import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;


/**
 * 单一内置过滤器 GatewayFilter
 * 1.类名需要以 GatewayFilterFsctory 结尾（该过滤器配置名称叫做 My）
 * 2.根据一个状态值/标志位-status，它等于多少，匹配和才可以访问
 * 3.继承 AbstractGatewayFilterFactory，重写 apply()
 *
 */
@Component
public class MyGatewayFilterFactory extends AbstractGatewayFilterFactory<MyGatewayFilterFactory.Config> {

    public MyGatewayFilterFactory() {
        super(MyGatewayFilterFactory.Config.class);
    }


    @Override
    public GatewayFilter apply(MyGatewayFilterFactory.Config config) {
        // 重写 apply()，返回一个 GatewayFilter -
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();
                System.out.println("进入了自定义网关过滤器 MyGatewayFilterFactory，status：" + config.getStatus());
                // 要求这个请求必需携带参数 jieHFUT=... 才会放行
                if (request.getQueryParams().containsKey("jieHFUT")){
                    return chain.filter(exchange);
                } else {
                    exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST); // 400 - 客户端异常
                    return exchange.getResponse().setComplete();
                }
            }
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        // 声明可以使用短配置
        return Arrays.asList("status");
    }

    public static class Config {
        @Getter
        @Setter
        private String status; //设定一个状态值/标志位，它等于多少，匹配和才可以访问
    }
}




