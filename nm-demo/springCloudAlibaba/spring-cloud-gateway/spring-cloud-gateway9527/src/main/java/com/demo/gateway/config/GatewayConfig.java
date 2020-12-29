//package com.demo.gateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 网关使用编码的方式实现《不推荐使用》
// * <p>
// *     这里只是掩饰一下这种方式，并不推荐这样使用
// * </p>
// * @description 官网 https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.1.RELEASE/reference/html/#gatewayfilter-factories
// * @author: gl_stars
// * @data: 2020年 10月 14日 13:29
// **/
//@Configuration
//public class GatewayConfig {
//
//    /**
//     * 配置一个ID为 path_route_george 的路由规则
//     * 当访问地址:http://localhost:9527/guonei 时会自动转发到地址 http://news.baidu.com/guonei
//     *
//     * @param routeLocatorBuilder
//     * @return
//     */
//    @Bean
//    public RouteLocator customeRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
//        return routeLocatorBuilder.routes()
//                .route("path_route_george", r -> r.path("/guonei").uri("http://news.baidu.com/guonei"))
//                .build();
//    }
//}
