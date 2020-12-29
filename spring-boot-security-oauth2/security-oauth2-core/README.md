# security-oauth2-core

# 一、redis管理令牌

## 1.2、简单配置

创建`TokenConfig`类指定`Redis`存储`Token`添加 `redis` 依赖后, 容器自动就会有 `RedisConnectionFactory` 实例。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200606154111501.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70)

### 将令牌管理策略添加到端点上

将上面令牌管理策略作用到认证服务器`AuthorizationServerConfig`端点上`configure(AuthorizationServerEndpointsConfigurer endpoints)`。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200606154420995.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70)



但是这样会有很多疑问，我们的刷新token默认是30天，访问token默认是12小时，这些值我们需要动态配置。这些时间都太大了，不适用。还有我们如何续签token，续签还是不续签。拿到token后如何查询用户的信息等等问题，我们写了一个自定义了处理 `CustomRedisTokenStore`。



## 2.2、当前配置说明

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201005201348871.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

原本`TokenStore`这个接口针对`redis`写了一个显现类，如果我们没有创建这个类，重新实现这个接口的话，默认就是使用 `org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore`类。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201005201538279.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

我们自定义的这个类在 `AuthRedisTokenStore`类中注入容器，在认证服务器中将接口绑定上。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201005201700131.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201005201816697.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201005201937377.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

其实我们获取到token就可以看到token的有效时间。

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020100520204367.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)