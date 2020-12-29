package com.demo.order.controller;

import com.demo.order.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 订单
 *
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@RestController
@RequestMapping("order")
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 创建订单
     * @param userId 用户id
     * @param commodityCode 订单编号
     * @param count 数量
     */
    @RequestMapping("/create")
    public Boolean create(String userId, String commodityCode, Integer count) {
        orderService.create(userId, commodityCode, count);
        return true;
    }
}
