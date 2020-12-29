package com.test.security.service.impl;

import com.test.security.config.SpringSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 用户认证的实现类
 * @author: stars
 * @data: 2020年 10月 01日 15:27
 **/
@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder ;

    /**
     * 这里根据账号去数据库查询密码和权限信息
     * <p>用户输入的是什么，我们并不用担心。我们只需要知道用户输入的账号就可以了，因为账号是唯一的
     * 这里的userName就是账号。我们将用户输入的账号拿去库里面查询密码和权限出来，保存到{@link UserDetails}
     * 中，具体判断登录信息是否正确不用我们管，框架里面会自动判断。仔细观察 {@link SpringSecurityConfig#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)}
     * 配置中，我们固定死的用户名、密码和权限的时候，我们也没有判断用户名、密码和权限是否正确，都是框架自动解决的。</p>
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String password = passwordEncoder.encode("123456");

        // 将用户名、密码和权限注入框架中
        return new User(userName,password, AuthorityUtils.createAuthorityList("ADMIN"));
    }
}
