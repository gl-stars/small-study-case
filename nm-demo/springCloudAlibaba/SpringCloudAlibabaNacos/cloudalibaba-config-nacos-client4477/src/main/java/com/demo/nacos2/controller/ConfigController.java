package com.demo.nacos2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author: stars
 * @data: 2020年 10月 11日 12:34
 **/
@RestController
public class ConfigController {

    @Autowired
    private RestTemplate restTemplate ;

    @GetMapping("/brook2/{name}")
    public Object find(@PathVariable("name") String name){
        // 注意不要少了路径的 / 了
        return restTemplate.getForEntity("http://nacosThree/brook3/" + name,String.class);
    }
}
