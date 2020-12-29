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