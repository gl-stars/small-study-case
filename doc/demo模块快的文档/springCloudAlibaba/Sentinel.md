# Sentinel使用

# 一、简介

官网：[https://github.com/alibaba/sentinel](https://github.com/alibaba/sentinel)

中文文档：[https://github.com/alibaba/Sentinel/wiki/介绍](https://github.com/alibaba/Sentinel/wiki/介绍)

中文文档也是从`GitHub`上点击链接跳转过来的，`GitHub`哪里有一个“中文文档”这样的字眼，直接点击就可以跳转到这里了。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016123106253.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

## 1.1、下载和安装

安装需要注意：`Sentinel`是`java`写的，所以需要`JDK1.8`以上的的版本。默认端口号为 `8080`，所以地址需要注意 `8080`端口号不能被占用。

- 下载

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016122650245.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016122836569.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016122902447.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

- 安装

下载下来直接是一个`jar`包，启动就和普通的`jar`启动方式样，不用过多介绍。

```shell
nohup java -jar xxxx.jar &
```

我在`Windows`上测试，所以我就直接使用 `java -jar XXX.jar `启动了。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016124107517.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016124157293.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



## 1.2、Sentinel 的主要特性

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016123416450.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

## 1.3、Sentinel生态

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016123443217.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

## 1.4、解决那些问题

- 服务雪崩
- 服务降级
- 服务熔断
- 服务限流



# 二、创建后端工程

创建 `spring-cloud-alibaba-sentinel`工程。

## 2.1、引入依赖

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
```

如果直接使用 `sentinel`，那么这个包就可以了。但是在`sentinel`里面的配置我们需要持久化，否则我们重新启动自己的jar，`sentinel`里面的配置就没有了。所以还需要引入一下这个依赖，将`sentinel`里面的配置同步到`nacos`中，`nacos`是可以将数据持久化到`mysql`数据库的，所以就不用担心数据丢失的问题。

```xml
<!-- 将sentinel的数据同步到nacos中  -->
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-datasource-nacos</artifactId>
</dependency>
```

<center style="color:blue;font-weight:bold;font-size:20px">强调</center>

如果还需要使用 `openfeign`怎么办，还需要引入 `openfeign`的依赖吗？

```xml
<!--   openfeign     -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

`spring-cloud-starter-alibaba-sentinel`这个依赖中已经包含了常用的 `spring-boot-starter-web`、`spring-cloud-starter-openfeign`、`spring-cloud-starter-netflix-ribbon`。但是需要注意的是，里面的 `<optoinal>true</optoinal> `设置成了`true`，所以下面是不能被继承的。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016130115693.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016130125336.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016130136275.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



> 没有直接引入版本号，因为父工程引入springcloudalibaba的包管理器了。
>
> ```xml
> <dependency>
>     <groupId>com.alibaba.cloud</groupId>
>     <artifactId>spring-cloud-alibaba-dependencies</artifactId>
>     <version>${spring-cloud-alibaba-dependencies.version}</version>
>     <type>pom</type>
>     <scope>import</scope>
> </dependency>
> ```

## 2.2、将程序注入sentinel中

直接在配置文件中配置一下就可以了，其他什么都不需要添加，包括启动程序都不需要添加任何东西。

```yaml
server:
  port: 8401

spring:
  application:
    name: sentinel-service # 当前工程名称
  cloud:
    sentinel:
      transport:
        dashboard: localhost:8080 # sentinel地址
        port: 8719  # Sentinel api端口 ,默认8719，假如被占用了会自动从8719开始依次+1扫描。直至找到未被占用的端口

management:
  endpoints:
    web:
      exposure:
        include: '*' # springboot 监控 Actuator 的端点暴露，* 为暴露所有端点。
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016150744884.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020101615083717.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016150921844.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



## 2.3、测试

首先启动`Sentinel`，第一次启动`sentinel`，在浏览器上访问 `IP:8080`，你会发现什么都没有，因为我们程序还没有启动。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016151341282.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

在启动我们程序，在刷新`sentinel`控制台，依然没有任何东西，因为我们要访问接口了才有数据。启动程序，访问一次接口。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016151732274.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

这样就注入成功了。

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020101615584573.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016155935710.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

# 三、流控规则

## 3.1、流控模式

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016163912960.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016155528179.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016155726578.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

### 直接

给`/testA`添加一个链路。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016160150613.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016160345550.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020101616042598.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016160538940.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

### 关联

如果 `/testA` 关联 `/testB`,这里只能通过一个QPS通过。那么如果当前 `/testB`每秒有 1一个以上的的请求在持续访问，那么 `/testA`就不能访问了。

- 创建关联控流

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016161503808.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



- postman做连续发送请求

![](https://img-blog.csdnimg.cn/20201016161605415.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![](https://img-blog.csdnimg.cn/20201016161634625.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016161900325.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016162058486.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016162139319.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



- 测试

现在的配置意思是，`/testB`每秒超过 1个QPS，那么 `/testA`就不能访问。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016162830953.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020101616300971.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016163124656.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



### 链路

无

## 3.2、流控效果

### 预热

预热时间计算：单机阈值  / 3  = 刚开始阈值。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016164600117.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



- 应用场景

秒杀系统，刚开始的时候，可能有很多数据在缓存哪里可能没有，所以刚开始的时候放少量的QPS过来，过几秒后没问题了，缓存中说句丰富了。

### 排队等待

匀速通过，不管来多少个，每次只能请求几个。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016165235304.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

# 四、降级规则

## 4.1、RT平均时长

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016170656932.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

上图有误，纠正

每秒中请求QPS大于 5 个，但是在200毫秒内没有响应，那么就降级处理。1秒钟后恢复。

## 4.2、异常比例（单位秒级）

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016171306814.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

## 4.3、异常数

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016171509697.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



# 五、热点规则

## 5.1、普通配置

热点规则，就是控制某个接口上的某个参数每秒中可以正常访问多少个QPS。需要使用到 `com.alibaba.csp.sentinel.annotation.SentinelResource`注解。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016181508847.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016181846189.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

配置好规则后，我执行以下请求

```http
http://localhost:8401/ok?name=sadfa
```

每秒中只能请求一次。

```http
http://localhost:8401/ok
```

随便请求，不影响。

## 5.2、参数例外项

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201016194131302.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



## 异常统一处理

目前我测试没有效果

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020101619591138.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)