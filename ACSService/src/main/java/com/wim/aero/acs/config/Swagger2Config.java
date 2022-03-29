package com.wim.aero.acs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @title: Swagger2Config
 * @author: Ellie
 * @date: 2022/03/29 15:12
 * @description:
 **/
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value(value = "${swagger.enable}")  //通过 @Value  获取配置信息
    private Boolean swaggerEnable;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .ignoredParameterTypes(HttpSession.class, HttpServletRequest.class)  //在生成的文档将哪些类对象的属性排除
                // 是否开启
                .enable(swaggerEnable)
                .select()
                // 扫描的路径包,只要这些包中的类配有swagger注解，则启用这些注解
                .apis(RequestHandlerSelectors.basePackage("com.wim.aero.acs.controller"))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("门禁业务后台API文档")
                .description("门禁业务后台api")
                // 作者信息
                .contact(new Contact("Ellie", "", ""))
                .version("1.0.0")
                .build();
    }
}
