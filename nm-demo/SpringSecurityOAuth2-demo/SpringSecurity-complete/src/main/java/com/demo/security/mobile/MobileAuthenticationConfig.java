package com.demo.security.mobile;

/**
 * @author: stars
 * @data: 2020年 10月 03日 19:31
 **/

import com.demo.security.authentication.CustomAuthenticationFailureHandler;
import com.demo.security.authentication.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
     * 认证成功处理类
     */
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    /***
     * 认证失败处理类
     */
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService mobileUserDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();
        // 获取容器中已经存在的AuthenticationManager对象，并传入 mobileAuthenticationFilter 里面
        mobileAuthenticationFilter.setAuthenticationManager(
                http.getSharedObject(AuthenticationManager.class));

        // 传入 失败与成功处理器
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        mobileAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);

        // 构建一个MobileAuthenticationProvider实例，接收 mobileUserDetailsService 通过手机号查询用户信息
        MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setUserDetailsService(mobileUserDetailsService);

        // 将provider绑定到 HttpSecurity上，并将 手机号认证过滤器绑定到用户名密码认证过滤器之后
        http.authenticationProvider(provider)
                .addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
