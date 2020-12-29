package com.demo.nacos2.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 *     @RefreshScope 表示实时刷新
 *     @LoadBalanced表示这个RestTemplate开启负载均衡，在调用服务提供者的接口时，可使用 服务名称
 * 替代真实IP地址。服务名称 就是服务提供者在application.yml中配置的spring.application.name属性的值
 * </p>
 * @author: stars
 * @data: 2020年 10月 11日 09:37
 **/
@Configuration
public class NacosConfig {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
