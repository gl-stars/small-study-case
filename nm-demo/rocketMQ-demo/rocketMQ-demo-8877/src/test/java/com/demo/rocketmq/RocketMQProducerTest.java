package com.demo.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: stars
 * @data: 2020年 12月 30日 09:49
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RocketMQApplication.class})
public class RocketMQProducerTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void test1(){
        for (int i = 0; i < 100; i++) {
            rocketMQTemplate.convertAndSend("springboot-mq","hello springboot rocketmq"+"   "+i);
        }
    }
}
