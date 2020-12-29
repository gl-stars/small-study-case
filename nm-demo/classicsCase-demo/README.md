# classicsCase-demo

该工程主要列举一些常见的小功能演示。

# 一、定义注解

使用 `HandlerMethodArgumentResolver`接口定义一个注解，并且该注解提供返回值。因为之前使用 `AOP`面向切面编程中环绕通知也可以实现同样的效果，但是那种方式不能将注解放在方法的形参上面。而这个接口实现的注解的方式，可以将注解放在方法形参上面，但是获取不到方法的参数。如果要想实现将注解放在方法形参上，也要获取到方法的参数。那么可以结合 `AOP`实现这个效果。因为 `AOP`可以动态给他一个方法的全类名，它可以获取到该方法中的参数和参数值等，还可以使用`SPEL`语法实现。



# 二、实现

## 2.1、定义一个注解

```java
package com.classics.annotation;

import java.lang.annotation.*;

/**
 * @author: gl_stars
 * @data: 2020年 09月 15日 14:53
 **/
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomTest {

    String name() default "";
}
```

## 2.2、写 `HandlerMethodArgumentResolver`实现类

```java
package com.classics.realization;

import com.classics.annotation.CustomTest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: gl_stars
 * @data: 2020年 09月 15日 14:54
 **/
public class TestArgumentResolver implements HandlerMethodArgumentResolver {

    /***
     * 支持参数
     * <p>
     *     当返回值为true执行 {@link TestArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)}
     * </p>
     * @param methodParameter 参数集合
     * @return 格式化后的参数
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 如果方法上包含 @CustomTest 注解，则返回true
//        boolean b = methodParameter.hasParameterAnnotation(CustomTest.class);

        // 该注解所在的形参类型为String，返回true
//        boolean assignableFrom = methodParameter.getParameterType().isAssignableFrom(String.class);

        // 打上 @LoginUser 这个注解，并且方法上的形参数据类型为 SysUser
//        methodParameter.hasParameterAnnotation(LoginUser.class) && methodParameter.getParameterType().equals(SysUser.class);

        // 我这里就不管数据类型了，只管是否打上该注解。
        return methodParameter.hasParameterAnnotation(CustomTest.class);
    }

    /***
     * 分解参数
     * <p>
     *     返回的就是注解所在方法上的值
     * </p>
     * @param methodParameter 入参集合
     * @param modelAndViewContainer model 和 view
     * @param nativeWebRequest web相关
     * @param webDataBinderFactory 入参解析
     * @return 包装对象
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        // 获取该注解的对象
        CustomTest parameter = methodParameter.getParameterAnnotation(CustomTest.class);

        // 获取request对象
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        // 向Header 中获取数据
//        request.getHeader("");
        Object str = request.getAttribute("str");
        System.out.println(str);
        return "哈哈哈哈哈哈哈哈哈哈";
    }
}
```

这里只有两个方法，当 `supportsParameter`方法的返回值为`true`时，才会执行 `resolveArgument`方法。

所以是否需要实现该注解的功能，全靠 `supportsParameter`这个方法是否返回`true`。但是如果这里一直返回`true`，那么不管你是否有自己定义的注解都去 `resolveArgument`执行这个方法了，这是不符合逻辑的。

```java
methodParameter.hasParameterAnnotation(CustomTest.class)
```

> 表示是否包含 `CustomTest`注解。

```java
methodParameter.getParameterType().isAssignableFrom(String.class)
```

> 表示当前注解的这个形参类型是否为 `String`，如果是返回 `true`

```java
methodParameter.hasParameterAnnotation(LoginUser.class) && methodParameter.getParameterType().equals(SysUser.class)
```

> 参数类型也可以是对象类型。

- `resolveArgument`方法

这个注解的所有处理逻辑就是在这个方法里面去完成了，需要注意：这个方法的返回值是`Object`类型，这里的返回值就是当前注解所在的返回值。



# 2.3、将处理逻辑加入容器

```java
package com.classics.realization;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 默认SpringMVC拦截器
 * <p>一定要将自己的扩展类加到容器中</p>
 * @author starts
 */
@Configuration
public class DefWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * 添加解析器支持自定义控制方法的参数类型。
     * <p>将</p>
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new TestArgumentResolver());
    }
}
```

这里一定要将 `HandlerMethodArgumentResolver`的实实现类添加到 `springmvc`拦截器中。