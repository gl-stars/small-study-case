package com.demo.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 库存
 *
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StorageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApplication.class, args);
    }
}
