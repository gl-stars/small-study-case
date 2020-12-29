package com.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: stars
 * @data: 2020年 10月 03日 18:35
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {


    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Test
    public void passwordEncoder(){
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }
}
