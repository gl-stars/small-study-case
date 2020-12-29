package com.demo.gateway8001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>
 *     @EnableFeignClients 使用 OpenFeign
 *     @EnableDiscoveryClient 注入nacos
 * </p>
 * @author: gl_stars
 * @data: 2020年 10月 14日 13:46
 **/
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class Gateway8001Application {

    public static void main(String[] args) {
        SpringApplication.run(Gateway8001Application.class,args);
    }
}
