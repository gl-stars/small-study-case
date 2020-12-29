package com.demo.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 订单
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@FeignClient(name = "order-service")
public interface OrderFeignClient {

    @GetMapping("order/create")
    Boolean create(@RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode, @RequestParam("count") Integer count);
}
