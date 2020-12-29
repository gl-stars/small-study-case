package com.demo.gateway8001.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 调用 gateway8002 这个微服务
 * <p>
 *     这里虽然还有降级处理等等，这里不讲。写限流降级的时候在说。
 * </p>
 * @author: gl_stars
 * @data: 2020年 10月 14日 13:49
 **/
@Component
@FeignClient(value = "gateway8002")
public interface PaymentFeignService {

    /**
     * 调用 gateway8002 微服务的 Get请求的 /user 这个地址
     * @return
     */
    @GetMapping("/user")
    public String user();
}
