package com.demo.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动类
 * <p>
 *     @EnableDiscoveryClient 注册到nacos
 *     @EnableFeignClients 使用openfeign
 * </p>
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AccountServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }
}
