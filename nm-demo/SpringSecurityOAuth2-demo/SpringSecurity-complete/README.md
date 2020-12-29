# SpringSecurity-complete

现在的登录并没有使用token，而是登录成功后再浏览器设置一个`cookie`，后端有相应的`session`判断是否登录。所以只要在同一个浏览器或者postman中登录后，我们访问其他接口并不需要做任何操作就可以了。

------

这是 `SpringSecurity`部分的最后一个模块了，在这里我删除了一下几个类。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201003220657408.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



# 一、怎么实现URL级别权限

在 `SpringSecurityConfig`类中的 `configure(HttpSecurity http)`方法配置的。这个方法里面有一个 `antMatchers("/user").hasAuthority("sys:user")`的方法，这个说明 `/user`路径只能由 `sys:user`权限访问。所以我就借助这个方法，在程序启动的时候，我就将 `sys_permission`表中`URL`和`code`指定到这里，因为它具有缓存的效果，性能比较可以的。我在这里添加的时候做了一个过滤，`URL`不等于空的数据才添加进来。如果这里面没有的`URL`地址的，通过 `.anyRequest().authenticated()`方法配置为登录才能访问。这样如果没有添加到 `antMatchers("/user").hasAuthority("sys:user")`这里面的`URL`地址，登录成功后是能访问的。但是我们这里做了一个菜单权限，就是你具有哪些角色可以访问哪些菜单。如果没有权限的菜单是不知道访问的URL地址的，但是针对这些菜单中，需要再次过滤出针对某个功能，例如：删除或者修改这样的功能设置权限，就需要提添加URL级别的权限了。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201004134916988.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



# 二、数据库密切相关

## 2.1、需要解决一下问题

- 登录如何认证？如何认证用户名和密码？
- 如何做到权限认证？
- 如果更改数据源怎么处理，从`mysql`更改为`MongoDB`？

## 2.2、登录如何认证

登录认证实现思想，定义 `AbstractUserDetailsService`类，继承 `UserDetailsService`接口。实现接口中的 `loadUserByUsername(String username)`方法，这里我们可以获取到账号。这里就是用用户输入的`username`，当然这个字段是可以更改的。这个字段就是账号或者手机号，是唯一的。我们将这个号拿去数据库中查询用户信息，这一个密码等信息就有了。因为这个实体类也是继承 `UserDetails`接口的，所以类型方面不用担心。将数据查询出来，`SpringSecurity`中会自动认证是否通过。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201004140327226.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201004141839951.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

上面说的URL权限级别认证，在 `SpringSecurityConfig`类中查询所有权限并放入 `SpringSecurity`中。这里是表示什么样的角色可以执行什么样的`URL`操作，这是针对全部。具体哪个人有什么样的角色呢？每个人的角色信息就是在这里添加进去的。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201004142222652.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



上面这这些就可以获得登录如何认证的，权限如何解决的。当然了，权限解决有两个步骤，第一个是根据每个人的分角色查询所拥有的权限，然后在权限用获取更目录子目录给前端。因为没有权限的菜单，前端是没有地址的。第二个步骤，就是将所有的权限带有URL的角色code和URL地址加入 `Springsecurity`中，然后登录的时候我们就可以知道自己是否有权限访问，没有权限访问目前是提示一个登录页面，但是后期会指定一个JSON格式的数据返回。

## 2.3、如何更换数据源

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201004144134453.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201004144212712.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



注意：查询所有权限，就是查询这张表中所有权限，并不是指定某个人。



# 三、动态更换登录方式

如果使用手机短信发送验证码的方式登录，我门可以不用更换登录方式，毕竟这种方式伪造的可能性小。如果使用用户名、密码和验证码的方式登录。先验证码上一次登录的MAC地址，如果当前登录上次的登录机器和这次的登录机器不同，那么我们需要发送一个验证码到上次的手机号上，通过验证码校验。

验证码问题：如果是在手机上，少数用后输入可能会造成问题。所以手机采用运算的验证码，例如：1+2=？ 方式。PC端使用固定的验证码。

- 实现方式

验证码登录，我们有一个验证码校验过滤器，这里我们可以获取到相关数据，因为这里是request对象。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201004180907419.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

如果在这里校验不通过，就报一个异常，指定一个状态码。前端获取到后会自动弹出一个框，提示用户发送短信验证。这个时候，验证码就是两个了，一个是手机号验证码，一个是普通验证码。如果只传一个手机验证码过来，我们还要在Redis中创建一条记录，XXX账号正在做手机号校验等，比较麻烦。因为前端实现这样的功能也比较轻松的，所以就交给前端处理这个问题。

- 问题：如果认证成功后，拿到token后，将这个token放到其他机器上使用呢？怎么处理？？？？

这个token我们不会使用原始的token，使用原始token也行。我们会将这个token作为key，将用户的Mac地址作为value保存到Redis中，校验的时候就查询用户的token和Mac地址是否准确。但是每次用户的Mac地址都是通过加密的方式传给我们。通过heard等方式传输给后端。



# 四、多次登录失败冻结账号

不管是哪种登录方式，如果登录失败指定次数，则将账号冻结。如果是发送短信验证码的，发送指定次数都输入不对就指定时间内不能发送验证码。如果是普通验证码这个效果就可以避免了。

不管是用户名和短信验证码还是用户名和验证码登录，只要登录失败指定次数就将账号冻结了，冻结在实体类中有指定字段的。登录失败可以在登录失败处理类那里去处理，失败一次像Redis中插入一个标识，并且指定过期时间。累计次数够了就直接冻结，到时间了在去解除冻结时间，当然，后端可以开启解除冻结的功能。验证码输入次数过多，有一个手机短信验证码类，我们可以在哪里很好的就可以解决这个问题。



# 注意：

现在如果你更改了个人的权限，需要重新登录。因为目前的数据是缓存起来的，更改数据并没有更改缓存。如果在权限表中添加或者更改，需要重启系统，因为这张表示在系统启动时就添加到缓存中了。但是我也不建议将这些缓存数据添加到Redis，因为每次请求一个接口都要从Redis去取，性能可能会有影响。