# nacos的使用

官网：[nacos.io](nacos.io)

版本号匹配：[https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E](https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E)

# 一、注册与发现

- 引入依赖

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-dependencies</artifactId>
    <version>${spring-cloud-alibaba-dependencies.version}</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

- 引入服务的注册与发现

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```

- 引入配置的动态变更

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```



- 将服务注册到nacos中

只需要在启动类上打上 `@EnableDiscoveryClient`注解，`bootstrap.yml`文件中配置`nacos`的路径。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201011135154803.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201011135430520.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



这样就可以注入nacos中了。



# 二、实现服务间调用

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201011135559489.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201011135644179.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



对于配置文件的相关配置，后期不上，我这里下载nacos新版本没有下载到。