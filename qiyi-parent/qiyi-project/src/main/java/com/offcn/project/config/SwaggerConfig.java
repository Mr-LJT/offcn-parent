package com.offcn.project.config;

import io.swagger.annotations.Api;
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
 * @CreateTime: 2021/5/6 15:28
 * @Description:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean("项目模块")
    public Docket projectApic(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("项目模块")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.regex("/project.*"))
                .build()
                .apiInfo(apiInfo())
                .enable(true);
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("七易众筹系统平台接口文档")
                .description("提供用户模块/审核模块/项目模块/支付模块的接口文档")
                .termsOfServiceUrl("www.ljt.com")
                .version("1.0")
                .build();
    }
}
