package com.demo.gateway8002;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: gl_stars
 * @data: 2020年 10月 14日 13:48
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class Gateway8002Application {

    public static void main(String[] args) {
        SpringApplication.run(Gateway8002Application.class,args);
    }
}
