package com.control.oauth.openid;

import com.control.oauth.config.SpringSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * openId的相关处理配置
 * <p>在这里写完，需要在 {@link SpringSecurityConfig}类中添加到过滤连上才会生效</p>
 * @author: stars
 * @data: 2020年 10月 06日 14:37
 **/
@Component
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    /**
     * 用户信息的类
     * <p>
     *     用户信息是 {@link SysUserDO}但是 {@link SysUserDTO}
     *     类继承了 {@link org.springframework.social.security.SocialUserDetails}
     * </p>
     * @see SysUserDTO
     * @see SysUserDO
     * @see org.springframework.social.security.SocialUserDetails
     * @see UserDetails
     */
    @Autowired
    private SocialUserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) {
        //openId provider
        OpenIdAuthenticationProvider provider = new OpenIdAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        http.authenticationProvider(provider);
    }
}
