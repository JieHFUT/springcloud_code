package com.jiehfut.cloud.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置 swagger3
 * 微服务分组：什么什么请求是什么模块
 */
@Configuration
public class Swagger3Config
{
    @Bean
    public GroupedOpenApi PayApi()
    {
        return GroupedOpenApi.builder().group("支付微服务模块").pathsToMatch("/pay/**").build();
    }
    @Bean
    public GroupedOpenApi OtherApi()
    {
        return GroupedOpenApi.builder().group("其它微服务模块").pathsToMatch("/other/**", "/others").build();
    }
    /*@Bean
    public GroupedOpenApi CustomerApi()
    {
        return GroupedOpenApi.builder().group("客户微服务模块").pathsToMatch("/customer/**", "/customers").build();
    }*/


    @Bean // 文档描述说明
    public OpenAPI docsOpenApi()
    {
        return new OpenAPI()
                // 项目说明
                .info(new Info().title("cloud2024")
                        .description("通用设计rest")
                        .version("v1.0"))
                // 有问题去百度
                .externalDocs(new ExternalDocumentation()
                        .description("www.jiehfut.com")
                        .url("https://yiyan.baidu.com/"));
    }
}
