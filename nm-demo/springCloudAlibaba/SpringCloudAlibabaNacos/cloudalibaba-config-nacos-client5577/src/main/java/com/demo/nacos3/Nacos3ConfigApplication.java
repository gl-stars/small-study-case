package com.demo.nacos3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: stars
 * @data: 2020年 10月 11日 12:36
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class Nacos3ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(Nacos3ConfigApplication.class,args);
    }
}
