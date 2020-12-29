package com.test.security.authorize;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 关于系统管理模块的授权配置
 * @author stars
 */
@Component
public class SystemAuthorizeConfigurerProvider implements AuthorizeConfigurerProvider {

    @Override
    public void confiure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        // 有 sys:user 权限的可以访问任意请求方式的/role
        config.antMatchers("/user").hasAuthority("sys:user")
        // 有 sys:role 权限的可以访问 get方式的/role
        .antMatchers(HttpMethod.GET,"/role").hasAuthority("sys:role")
        .antMatchers(HttpMethod.GET, "/permission")
        // ADMIN 注意角色会在前面加上前缀 ROLE_ , 也就是完整的是 ROLE_ADMIN, ROLE_ROOT
        .access("hasAuthority('sys:premission') or hasAnyRole('ADMIN', 'ROOT')");
    }
}
