package com.demo.nacos1.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 读取yml中的配置，启动配置刷新以后，从nacos中配置，从这里获取到。
 * @author: stars
 * @data: 2020年 10月 11日 09:53
 **/
@Setter
@Getter
@Configuration
@RefreshScope   //通过SpringCloud原生注解 @RefreshScope 实现配置自动更新
@ConfigurationProperties(prefix = "nacos.info")
public class NacosDemoProperties {

    private String name ;

    private Double price ;
}
