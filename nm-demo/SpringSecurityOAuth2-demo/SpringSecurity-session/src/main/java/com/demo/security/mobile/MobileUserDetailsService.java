package com.demo.security.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 通过手机号获取用户信息和权限资源
 * 其实我们已经在 {@link MobileUserDetailsService#loadUserByUsername(java.lang.String)}写过了，应该不用写这个方法也
 * 可以的，但是没有测试过。
 * @author stars
 */
@Component
public class MobileUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        logger.info("请求的手机号是：" + mobile);
        // 1. 通过手机号查询用户信息
        // 2. 如果有用户信息，则再获取权限资源
        // 3. 封装用户信息

        return new User("admin", "", true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
