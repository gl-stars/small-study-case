package com.demo.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 业务
 * <p>
 *     @EnableDiscoveryClient 注册到nacos
 *     @EnableFeignClients 使用openfeign
 * </p>
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class BusinessServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessServiceApplication.class, args);
    }
}
