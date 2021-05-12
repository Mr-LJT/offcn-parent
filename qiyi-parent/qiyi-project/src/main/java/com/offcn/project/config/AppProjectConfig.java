package com.offcn.project.config;

import com.offcn.project.util.OssTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LJT
 * @CreateTime: 2021/5/6 15:51
 * @Description:
 */
@Configuration
public class AppProjectConfig {


    @ConfigurationProperties(prefix = "oss")
    @Bean
    public OssTemplate ossTemplate(){
        return new OssTemplate();
    }
}
