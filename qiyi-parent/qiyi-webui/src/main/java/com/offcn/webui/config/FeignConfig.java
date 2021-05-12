package com.offcn.webui.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LJT
 * @CreateTime: 2021/5/11 20:08
 * @Description:
 */
@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level getFeignLogger(){
        return Logger.Level.FULL;
    }
}
