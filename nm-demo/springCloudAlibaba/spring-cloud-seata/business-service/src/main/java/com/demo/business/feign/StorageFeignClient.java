package com.demo.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 库存
 *
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@FeignClient(name = "storage-service")
public interface StorageFeignClient {

    @GetMapping("storage/deduct")
    Boolean deduct(@RequestParam("commodityCode") String commodityCode, @RequestParam("count") Integer count);
}
