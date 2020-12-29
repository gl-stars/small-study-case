package com.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 加载认证信息配置类
 * <p>返回错误提示时，更改为中文，默认是英文。</p>
 * @author: stars
 * @data: 2020年 10月 03日 15:25
 **/
@Configuration
public class ReloadMessageConfig {

    /****
     * 加载中文的认证提示信息
     * @return
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        //.properties 不要加到后面
//        messageSource.setBasename("classpath:org/springframework/security/messages_zh_CN");
        messageSource.setBasename("classpath:messages_zh_CN");
        return messageSource;
    }
}