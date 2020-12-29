package com.demo.user.controller;

import com.centre.common.model.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 所有的前端控制器都写在这里了。
 * @author: stars
 * @data: 2020年 10月 03日 16:17
 **/
@RestController
public class DemoController {


    /**
     * 获取验证码
     * <p>本来正规流程是，通过验证码生成器生成验证码，这个验证码是一张照片，但是生成器会给我们照片上的验证码。
     * 我们需要将验证码存到Redis中，然后将这个图片通过流的方式写到前端。前端将验证码输入回来时，我们需要去Redis里面查询是否存在。
     * 这里为了简便，我就只做一个过程就行了，图形验证码生成就在当前项目中有现成的，为了过多的引用其他模块，就免了。</p>
     * @return
     */
    @RequestMapping("/code/image")
    public Object imageCode(){
        return 889900;
    }

    @GetMapping("/find")
    public Result find(){
        return Result.succeed("查询成功");
    }

    @GetMapping("/user")
    public Result findUser(){
        return Result.succeed("能够看到吗？");
    }
    @GetMapping("/find/sysuser")
    public Result findUSysUser(){
        return Result.succeed("能够访问到吗？");
    }
    @PatchMapping("/update")
    public Result updateOne(){
        return Result.succeed("更改OK");
    }


    /***
     * 获取当前用户信息方式一（推荐）
     * @param map
     */
    @GetMapping("/index")
    public Object index(Map<String, Object> map) {
        // 第1方式：
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != null && principal instanceof UserDetails) {
            // 这里就已经获取到UserDetails对象了，所以里面的所有数据都有了
            UserDetails userDetails = (UserDetails)principal;
            String username = userDetails.getUsername();
            map.put("username", username);
        }
        return principal ;
    }

    /**
     * 获取当前用户 方式二
     * @param authentication
     * @return
     */
    @GetMapping("/user/info")
    public Object userInfo(Authentication authentication) {
        return authentication.getPrincipal();
    }

    /**
     * 获取当前用户方式三
     * @param userDetails
     * @return
     */
    @GetMapping("/user/info2")
    public Object userInfo2(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

}
