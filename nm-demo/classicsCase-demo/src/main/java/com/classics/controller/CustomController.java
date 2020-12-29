package com.classics.controller;

import com.classics.annotation.CustomTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: gl_stars
 * @data: 2020年 09月 15日 15:05
 **/
@RestController
public class CustomController {

    @GetMapping("/find")
    public Object find(@CustomTest(name = "哈哈哈") String str){
        System.out.println(str);
        return true ;
    }

    @GetMapping("/list")
    public Object list(@CustomTest String str){
        System.out.println(str);
        return true ;
    }

    @GetMapping
    public Object no(){
        return "如果没有使用@CustomTest注解，就不会再去执行那个处理类了。";
    }
}
