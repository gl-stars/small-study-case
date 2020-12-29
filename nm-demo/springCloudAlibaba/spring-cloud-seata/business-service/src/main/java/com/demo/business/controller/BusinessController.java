package com.demo.business.controller;


import com.demo.business.service.BusinessService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@RestController
public class BusinessController {
    @Resource
    private BusinessService businessService;

    /**
     * 下单场景测试-正常
     */
    @RequestMapping(path = "/placeOrder")
    public Boolean placeOrder() {
        businessService.placeOrder("U001");
        return true;
    }

    /**
     * 下单场景测试-回滚
     */
    @RequestMapping(path = "/placeOrderFallBack")
    public Boolean placeOrderFallBack() {
        businessService.placeOrder("U002");
        return true;
    }
}
