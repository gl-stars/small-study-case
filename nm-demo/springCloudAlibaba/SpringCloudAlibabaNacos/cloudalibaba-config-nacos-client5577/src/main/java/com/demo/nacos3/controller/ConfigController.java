package com.demo.nacos3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: stars
 * @data: 2020年 10月 11日 12:34
 **/
@RestController
public class ConfigController {

    @GetMapping("/brook3/{name}")
    public Object find(@PathVariable("name") String name){
        System.out.println("服务器nacosThree被调用************");
        return name ;
    }
}
