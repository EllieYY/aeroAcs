package com.wim.aero.acs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @Description : api文档配置类
 * @Author : Ellie
 * @Date : 2019/2/18
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    //配置Swagger的Docket的bean实例
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()); // 配置Swagger信息
    }

    // 配置Swagger信息
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "门禁系统",
                "门禁系统服务AB API文档",
                "1.0",
                "iafc",//团队信息的url
                new Contact("Ellie", "", ""),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<VendorExtension>());
    }
}
