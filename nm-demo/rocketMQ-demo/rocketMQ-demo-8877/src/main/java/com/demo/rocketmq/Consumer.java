package com.demo.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "springboot-mq",consumerGroup = "springboot-mq-consumer-1")
public class Consumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("Receive message："+message);
    }

    @Scheduled(cron = "1/1 * * * * ? ")
    public void getInfo(){
        System.out.println("我靠************");
    }
}