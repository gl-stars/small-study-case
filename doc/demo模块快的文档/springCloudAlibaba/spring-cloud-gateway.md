# springCloudAlibaba

视频地址：https://www.bilibili.com/video/BV18E411x7eT

源码地址：https://github.com/acloudyh/springCloud

# 一、spring-cloud-gateway

- 三大核心
  - route路由
  - predicate断言
  - filter过滤

## 1.1、引入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

因为我们还要测试负载均衡，需要注入多个实例到注册中心，所以还需要引入注册中心`nacos`

```xml
<!-- 实现服务的注册与发现 -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

注意：绝对不能引入一下两个依赖，否则会报一下错误。

```lua
Parameter 0 of method modifyResponseBodyGatewayFilterFactory in org.springframework.cloud.gateway.config.GatewayAutoConfiguration required a bean of type 'org.springframework.http.codec.ServerCodecConfigurer' that could not be found.
```

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
绝对不能引入
```

## 1.2、创建启动类和配置路由

- 创建启动类

```java
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
```

`@EnableDiscoveryClient`注入到注册中心`nacos`



- 创建路由配置

```yaml
server:
  port: 9527
spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true  #开启注册中心路由功能,利用微服务名进行路由（如果这里不开启，那么调用时只能使用微服务的  IP:端口  的方式进行调用）
      routes: # 路由配置
        - id: gateway8001a
          uri: lb://gateway8001 
          predicates: # 断言
            - Path=/api-gateway/**
          filters: # 过滤
            - StripPrefix=1 # 去掉一个前缀
            - PreserveHostHeader # 会保留原始请求的host头信息，并原封不动的转发出去，而不是被gateway的http客户端重置
```

路由的详细说明在代码里面已经说得很清楚了，这里就不在过多说明，就说一下配置技巧。

在使用的时候，我们一个微服务是有N多个访问地址的。我们在配置的时候，可以在访问地址前面添加一个前缀，在配置的时候，如果存在这个前缀的请求，我们就指定它执行某个微服务，并且需要将这个前缀去掉。`- StripPrefix=1 # 去掉一个前缀`如果只为 2 就是去掉 2 个前缀。



## 1.3、自定义过滤器

创建一个类继承 `GlobalFilter, Ordered`这两个接口就可以了。实现里面的两个方法， `filter(ServerWebExchange exchange, GatewayFilterChain chain)`和 `public int getOrder()`。

第一个方法表示拦截的业务逻辑，第二个方法表示当前过滤器执行的顺序，值越大优先级越低，执行越往后执行。

```java
package com.demo.gateway.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 自定义全局GlobalFilter
 * @author: gl_stars
 * @data: 2020年 10月 14日 13:29
 **/
@Component
public class MyLogGateWayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 获取路径中 uname 的参数
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
		// 如果请求中没有 uname ，就不放行
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
```



网关就说到这里，路由配置可以使用编码来实现。代码中有一个案例的，但是不推荐使用，所以文档就不在描述。

## 1.4、测试

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201014204544166.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201014204703554.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201014204740912.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

但是目前我的负载均衡没有实现，不知道为啥。`Ribbon`并没有直接引入，好像`gateway`里面也有吧，但是`nacos`里面是有的，所以没有直接引入。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201014205135238.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

# 二、OpenFeign

## 2.1、Feign与OpenFeign区别

明确的说，Feign进入维护了，OpenFeign没有。

> 这里是引用
> ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201014205554411.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



在使用的时候不难看出，`Feign`使用方法调用的形式，`OpenFeign`面向接口调用的方式。

## 2.2、分别使用

- Feign

`Feign`在`nacos`案例模块使用的。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201014205823224.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201014205920106.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/202010142101083.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

`getForObject()`方法指定的类型，如果没有其他额外要求，推荐使用这个。

`getForEntity()`返回的数据比较详细。



`Feign`的使用在nacos案例中就是用了，所以这里不再强调，更何况我们也不用。

- `OpenFeign`推荐使用

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201014212124442.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201014212326620.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201014212416835.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



将网管和微服务启动，开始测试。当然，不用启动网管也可以，直接访问第一个微服务的/user地址，就不走网关通过了。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201014212549509.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

## 2.3、解决服务间调用默认时间

`OpenFeign`默认在服务间调用为 1 秒，超过这个时间没有返回数据，那么这边就自行处理，没有降级工程的就报错，就当做调用微服务那边没有反应的处理。但是有些接口，可能处理时间大于 1 秒中，所以我们需要将这个超市时间更改一下。

```yaml
#设置feign客户端超时时间(OpenFeign 默认支持ribbon)
ribbon:
  #指的是建立连接所用时间,适用于网络状况正常情况下,两端连接所用时间
  ReadTimeout: 5000
  #指的是连接建立后,从服务器获取到可用资源所用时间
  ConnectTimeout: 5000
```

## 2.4、日志级别参考脑图



