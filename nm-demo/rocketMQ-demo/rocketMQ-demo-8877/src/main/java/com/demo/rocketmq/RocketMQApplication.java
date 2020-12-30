package com.demo.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 * @author: gl_stars
 * @data: 2020年 10月 23日 10:10
 **/
@EnableScheduling
@SpringBootApplication
public class RocketMQApplication {

    public static void main(String[] args) {

        SpringApplication.run(RocketMQApplication.class,args);
    }
}
