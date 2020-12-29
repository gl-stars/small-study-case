# SpringSecurity-demo

这里都不做任何文档描述，因为之前`GitHub`上的这两个案例做的足够了。这里只做一些当前练习的版本记录，那次提交解决了什么问题，是否可以单独做测试。总结一下整体实现思路等等。



注意：文档上说哪个版本号是实现什么功能就是实现什么功能，但是文档会后退一个版本。比如第一次提交的功能文档是在下一次提交里面。因为提交了得到版本号记录在问文档里面，下一次做一次提交。



- 回到历史版本

```
git reset --hard  版本号
```

注意：这个一定要使用`Git`原始窗口实现，或者`idea`工具中`DOS`命令也行，如果是观看可以回退到原来的代码里面，这样比较清洁。如果也想让远程仓库回到相应的版本，`git push -f`强制提交。但是这样提交了以后，从当前版本以后的代码就没有，记录都没有。需要谨慎操作，当然了，我的项目你们是只能观看，却不能改变的。

# 一、用户名和密码写成死的认证

- 提交说明

```
5507171874ee0d0a7833f701219cff3f15eb9ffe
```

实现最基本的认证，用户名和密码都是写成死的。

# 二、实现用户名和密码动态认证

- 版本号

```
1404f48d33b69c4d92670c18d8980ed3c5a42019
```

这里会发现，我们在动态操作的时候只能获取到账号，虽然这里的名字是 `userName`，但是这就是用户的账号。因为账号是唯一的，所以就可以用账号去获取到密码和权限，注入框架中。用户输入的具体对还是不对，那么是框架里面自动帮我们完成的事情，我们不需要管。`com.demo.security.service.impl.CustomUserDetailsService#loadUserByUsername`这里的文档写的非常清楚了。



# 三、实现图形验证码认证

```
3569c18a4bede3ba61b3d40ba2933b3f5d55f6f0
```

完成图形验证码认证，但是一直获取不到客户端的信息，可能是应为`postman`的原因，所以没有测试。

- 大概思路

创建一个类继承 `org.springframework.web.filter.OncePerRequestFilter`类，实现 `com.demo.security.file.ImageCodeValidateFilter#doFilterInternal`方法。然后没次请求都会来这里一次，判断是`post`提交，并且执行的方式是登录方法，那么就获取验证码与生成的验证码是否匹配，如果匹配就放行，不匹配就抛出异常，执行认证失败处理。



# 四、实现手机短信登录

```lua
ee8b4df43b4afbf84888d10dd1155df54b0f57a7
```

- 实现思路

第一步：

创建一个方法继承 `OncePerRequestFilter`类，每次访问都会来到这个类，在这里判断提交方式是否为`post`，提交路径是否为我们这里指定的路径。如果是，就判断手机验证码是否正确，正确就放行。不正确就抛出异常，认证失败类会捕获异常提示认证失败。

```java
public class MobileValidateFilter extends OncePerRequestFilter {}
```

第二步：

实现手机认证过滤器

```java
com.demo.security.mobile.MobileAuthenticationFilter
```

- 第三步

封装手机认证的`token`

```java
com.demo.security.mobile.MobileAuthenticationProvider
```

- 第四步

认证处理类提供者，获取到手机号，去查询，如果存在就将用户信息封装。

手机号获取用户信息 `MobileUserDetailsService`

- 第五步

自定义管理认证配置 `MobileAuthenticationConfifig`

创建 `com.mengxuegu.security.authentication.mobile.MobileAuthenticationConfifig` 类

将上面定义的组件绑定起来，添加到容器中.

- 第六步

绑定到安全配置 `SpringSecurityConfifig`，

```java
/**
* 注入验证码校验
*/
@Autowired
private MobileValidateFilter mobileValidateFilter;

/***
* 注入 关于手机登录的组件
*/
@Autowired
private MobileAuthenticationConfig mobileAuthenticationConfig;
```

```java
http.addFilterBefore(mobileValidateFilter, UsernamePasswordAuthenticationFilter.class)
http.apply(mobileAuthenticationConfig);
```



# 五、获取获取当前用户

一般获取用户都是我们后台操作的，所以方式一推荐，方式二和方式三就不推荐使用了。

```java
/***
 * 获取当前用户信息方式一（推荐）
 * @param map
 */
@GetMapping("/index")
public Object index(Map<String, Object> map) {
    // 第1方式：
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(principal != null && principal instanceof UserDetails) {
        // 这里就已经获取到UserDetails对象了，所以里面的所有数据都有了
        UserDetails userDetails = (UserDetails)principal;
        String username = userDetails.getUsername();
        map.put("username", username);
    }
    return principal ;
}

/**
 * 获取当前用户 方式二
 * @param authentication
 * @return
 */
@GetMapping("/user/info")
public Object userInfo(Authentication authentication) {
    return authentication.getPrincipal();
}

/**
 * 获取当前用户方式三
 * @param userDetails
 * @return
 */
@GetMapping("/user/info2")
public Object userInfo2(@AuthenticationPrincipal UserDetails userDetails) {
    return userDetails;
}
```



# 六、将权限配置拆分

```
ced89b43f433798fe51d867f38f093f2de5a618e
```

- 第一步

定义 `AuthorizeConfigurerProvider`授权配置统一接口。

- 第二步

针对每个功能模块都创建一个 `AuthorizeConfifigurerProvider` 接口的模块权限配置实现类，

如：用户模块权限配置实现类、角色模块权限配置实现类

- 第三步

将对应权限配置抽取到对应 AuthorizeConfifigurerProvider 的实现类中

- 第五步

 创建一个授权配置管理者接口 AuthorizeConfifigurerManager 将所有的授权配置统一的管理起来。通过 AuthorizeConfifigurerManager 接口实现类，将 AuthorizeConfifigureProvider 所有的授权配置实现类全部加载到容器中。

- 第六步

将AuthorizeConfifigurerManager 接口注入 `com.demo.security.config.SpringSecurityConfig#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)`方法中，否则不生效的。