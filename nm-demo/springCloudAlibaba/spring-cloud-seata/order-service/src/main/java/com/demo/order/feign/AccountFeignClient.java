package com.demo.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@FeignClient(name = "account-service")
public interface AccountFeignClient {
    @PostMapping("account/reduce")
    Boolean reduce(@RequestParam("userId") String userId, @RequestParam("money") Integer money);
}
