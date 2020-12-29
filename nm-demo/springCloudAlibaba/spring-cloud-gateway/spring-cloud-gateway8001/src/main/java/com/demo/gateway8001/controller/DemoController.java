package com.demo.gateway8001.controller;

import com.demo.gateway8001.service.PaymentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: gl_stars
 * @data: 2020年 10月 14日 13:49
 **/
@RestController
public class DemoController {

    @Autowired
    private PaymentFeignService paymentFeignService ;

    @GetMapping("/find")
    public String find(){
        return "可以访问到了吗？8001";
    }
    @GetMapping("/list")
    public String list(){
        return "这里应该是没有问题的吧8001";
    }

    /***
     * 测试 OpenFeign 调用
     * @return
     */
    @GetMapping("/user")
    public String user(){
        return paymentFeignService.user();
    }
}
