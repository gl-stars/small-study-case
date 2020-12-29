package com.test.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权配置统一接口，所有模块的授权配置类都要实现这个接口
 * @author stars
 */
public interface AuthorizeConfigurerProvider {

    /**
     * 参数为 authorizeRequests() 的返回值
     * @param config
     */
    void confiure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
