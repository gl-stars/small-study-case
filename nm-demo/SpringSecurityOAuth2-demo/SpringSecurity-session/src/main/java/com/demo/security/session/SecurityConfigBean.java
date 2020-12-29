package com.demo.security.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * 主要为容器中添加Bean实例
 * @author stars
 */
@Configuration
public class SecurityConfigBean {

    /**
     * 当session失效后的处理类
     * @return
     */
    @Autowired
    private SessionRegistry sessionRegistry;

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new CustomInvalidSessionStrategy(sessionRegistry);
    }
}