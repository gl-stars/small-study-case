package com.demo.nacos1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>
 *     通过 Spring Cloud 原生注解 @EnableDiscoveryClient 开启服务注册发现功能：
 * </p>
 * Document: https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html
 * @author: stars
 * @data: 2020年 10月 11日 09:45
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class Nacos1ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(Nacos1ConfigApplication.class, args);
    }
}