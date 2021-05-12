package com.offcn.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author LJT
 * @CreateTime: 2021/4/30 15:30
 * @Description:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean("用户模块")
    public Docket CreateRestApi(){
        //1 构建接口文档结构
        //2 选择哪个接口去生成
        //3 生成的接口在哪个包路径下
        //4 是否将该包下的所有方法都生成
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.offcn.user.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("七易众筹接口文档")
                .description("提供用户模块订单接口文档")
                .termsOfServiceUrl("www.ljt.com")
                .version("1.0")
                .build();
    }
}
