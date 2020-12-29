package com.demo.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * Ribbon 负载均衡替换
 * <b>
 *     注意：这个类一定不要让 @ComponentScan 注解扫描到。
 * </b>
 */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        // 轮询（默认）
//        return new RoundRobinRule();
        // 随机
        return new RandomRule();
        // 轮询，如果获取失败，在指定的时间内重新获取
//        return new RetryRule();
        // 过滤掉经常出现故障的服务，选择一个并发小的服务
//        return new BestAvailableRule();
        // 选择性能好的那个。
//        return new ZoneAvoidanceRule();
    }
}
 
 
