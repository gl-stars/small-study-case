package com.demo.security.mobile;

import com.demo.security.service.AbstractUserDetailsService;
import com.demo.user.model.SysUser;
import com.demo.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 *  通过手机号获取用户信息和权限资源
 */
@Component("mobileUserDetailsService")
public class MobileUserDetailsService extends AbstractUserDetailsService {

    @Autowired
    SysUserService sysUserService;

    @Override
    public SysUser findSysUser(String usernameOrMobile) {
        System.out.println("请求的手机号的是："+usernameOrMobile);
        // 1. 通过手机号查询用户信息
        return sysUserService.findByMobile(usernameOrMobile);
    }

}