package com.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * @author: gl_stars
 * @data: 2020年 09月 04日 17:05
 **/
@SpringBootApplication
@EnableMongoAuditing
public class MongodbApplication {
    public static void main(String[] args) {
        SpringApplication.run(MongodbApplication.class,args);
    }
}
