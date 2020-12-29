package com.control.oauth.feign;

import com.sun.istack.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户模块
 * @author: stars
 * @data: 2020年 10月 23日 21:01
 **/
@FeignClient(name = "user-service")
public interface SysUserService {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @GetMapping("/user/username")
    UserDetails findUserDetails(@NotNull String username);

    @GetMapping("/user/openId")
    SocialUserDetails selectByOpenId(String openId);
}
