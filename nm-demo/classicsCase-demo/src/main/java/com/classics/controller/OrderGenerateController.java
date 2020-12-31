package com.classics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单单号生成
 * @author: stars
 * @data: 2020年 12月 31日 14:54
 **/
@RestController
@RequestMapping("/order")
public class OrderGenerateController {


    @Autowired
    private RedisTemplate redisTemplate ;

    private String REDIS_DATABASE = "stars";

    private String REDIS_KEY_ORDER_ID = "order_id" ;

    /**
     * 生成18位订单编号:8位日期+2位平台号码+2位支付方式+6位以上自增id
     * @return 订单号：202012310202000006
     */
    @GetMapping
    public String generateOrderSn() {
        StringBuilder sb = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 拼接key值
        String key = REDIS_DATABASE+":"+ REDIS_KEY_ORDER_ID + date;
        Long increment = incr(key, 1);
        sb.append(date);
        // （动态匹配）添加两个平台号码，这个表示下单路径，例如：0->PC订单；1->APP订单；2->Applets(小程序订单)
        sb.append(String.format("%02d", 2));
        // （动态匹配）支付方式 1：支付宝，2：微信，3：空气支付
        sb.append(String.format("%02d",2));
        String incrementStr = increment.toString();
        if (incrementStr.length() <= 6) {
            sb.append(String.format("%06d", increment));
        } else {
            sb.append(incrementStr);
        }
        return sb.toString();
    }

    /***
     * 按delta递增
     * @param key
     * @param delta
     * @return
     */
    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }
}
