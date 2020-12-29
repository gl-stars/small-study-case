package com.demo.gateway.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义全局GlobalFilter
 * @author: gl_stars
 * @data: 2020年 10月 14日 13:29
 **/
//@Component // 为了测试方便，这个过滤器取消。
public class MyLogGateWayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");


        if (StringUtils.isEmpty(uname)) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);

            // 不放行
            return exchange.getResponse().setComplete();
        }
        // 放行
        return chain.filter(exchange);

    }

    /***
     * 当前过滤器执行顺序
     * @return 值越高优先级越低
     */
    @Override
    public int getOrder() {
        return 0;
    }
}