package com.demo.gateway;

import com.demo.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * 主启动类
 * <b>
 *     @EnableDiscoveryClient 注入nacos注册中心
 * </b>
 * @author: gl_stars
 * @data: 2020年 10月 14日 13:29
 **/
@EnableDiscoveryClient
@SpringBootApplication
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }

}