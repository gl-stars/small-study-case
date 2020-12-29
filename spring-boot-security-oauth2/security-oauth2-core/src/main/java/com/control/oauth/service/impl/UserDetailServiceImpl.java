package com.control.oauth.service.impl;

import com.control.oauth.feign.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;


/**
 * 用户动态认证
 * <p>
 *     1、手机号登陆
 *     2、账号登录
 *     3、第三方openid登录
 * </p>
 * @author: stars
 * @data: 2020年 10月 05日 22:54
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService, SocialUserDetailsService{

    @Autowired
    private SysUserService sysUserService;

    /***
     * 通过用户账号查询用户（用户名或者手机号）
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return sysUserService.findUserDetails(username);
    }

    /**
     * 第三方openId登录（用于查找用户详细信息的用户ID）
     * <p>相当于通过微信或者QQ登录</p>
     * @param openId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String openId) throws UsernameNotFoundException {
        return sysUserService.selectByOpenId(openId);
    }
}
