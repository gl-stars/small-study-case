package com.demo.business.service;

import com.demo.business.feign.OrderFeignClient;
import com.demo.business.feign.StorageFeignClient;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 业务
 *
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@Slf4j
@Service
public class BusinessService {
    private static final String COMMODITY_CODE = "P001";
    private static final int ORDER_COUNT = 1;

    @Resource
    private OrderFeignClient orderFeignClient;

    @Resource
    private StorageFeignClient storageFeignClient;

    /**
     * 下订单
     */
    @GlobalTransactional
    public void placeOrder(String userId) {
        storageFeignClient.deduct(COMMODITY_CODE, ORDER_COUNT);

        orderFeignClient.create(userId, COMMODITY_CODE, ORDER_COUNT);
    }
}
