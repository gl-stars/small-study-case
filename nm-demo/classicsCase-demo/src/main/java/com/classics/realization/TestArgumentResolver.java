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
