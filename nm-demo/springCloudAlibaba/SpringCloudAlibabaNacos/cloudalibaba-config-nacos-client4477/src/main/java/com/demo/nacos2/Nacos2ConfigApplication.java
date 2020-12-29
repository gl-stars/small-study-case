package com.demo.nacos2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: stars
 * @data: 2020年 10月 11日 12:35
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class Nacos2ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(Nacos2ConfigApplication.class,args);
    }
}
