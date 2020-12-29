package com.demo.security.mobile;

import com.demo.security.authentication.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 用于组合其他关于手机登录的组件
 * @author stars
 */
@Component
public class MobileAuthenticationConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    /***
     * 这是一个自定义的成功处理类，但是我没有创建这个类，我认为认证成功不需要做处理了。
     */
//    @Autowired
//    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService mobileUserDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();

        // 传入失败处理类
        mobileAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);

        // 构建一个MobileAuthenticationProvider实例，接收 mobileUserDetailsService 通过手机号查询用户信息
        MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setUserDetailsService(mobileUserDetailsService);

        // 将provider绑定到 HttpSecurity上，并将 手机号认证过滤器绑定到用户名密码认证过滤器之后
        http.authenticationProvider(provider)
                .addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
