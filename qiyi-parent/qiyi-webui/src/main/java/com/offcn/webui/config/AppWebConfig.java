package com.offcn.webui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author LJT
 * @CreateTime: 2021/5/11 20:05
 * @Description:
 */
@Configuration
public class AppWebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //目前的controller只用作于转发页面，需要在当前中配置webmvc
        registry.addViewController("login.html").setViewName("login");
    }
}
