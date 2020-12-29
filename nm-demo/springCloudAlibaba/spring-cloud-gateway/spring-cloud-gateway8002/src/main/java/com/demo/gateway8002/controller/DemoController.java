package com.demo.gateway8002.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: gl_stars
 * @data: 2020年 10月 14日 13:49
 **/
@RestController
public class DemoController {

    @GetMapping("/find")
    public String find(){
        return "可以访问到了吗？8002";
    }
    @GetMapping("/list")
    public String list(){
        return "这里应该是没有问题的吧8002";
    }

    @GetMapping("/user")
    public String user(){
        return "这里是返回给gateway8001 微服务的数据，当前是 gateway8002 微服务";
    }
}
