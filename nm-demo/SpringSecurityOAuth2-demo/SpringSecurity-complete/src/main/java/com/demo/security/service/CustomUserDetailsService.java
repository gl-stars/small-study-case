package com.demo.security.service;

import com.demo.user.model.SysUser;
import com.demo.user.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 根据用户名查询
 * @author: stars
 * @data: 2020年 10月 03日 18:55
 **/
@Component("customUserDetailsService")
public class CustomUserDetailsService extends AbstractUserDetailsService{
    Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired // 不能删掉，不然报错
//            PasswordEncoder passwordEncoder;

    @Autowired
    SysUserService sysUserService;


    @Override
    public SysUser findSysUser(String usernameOrMobile) {
        logger.info("请求认证的用户名: " + usernameOrMobile);
        // 1. 通过请求的用户名去数据库中查询用户信息
        return sysUserService.findByUsername(usernameOrMobile);
    }
}
