package com.demo.security.mobile;

import com.demo.security.authentication.CustomAuthenticationFailureHandler;
import com.demo.security.code.ImageCodeValidateFilter;
import com.demo.security.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证用户输入的验证码与生成的验证码是否一致
 * <p>继承这个类，那么每次访问都会调用一下这个类。如果验证码没有输入成功，则抛出异常，调用认证失败处理类。
 * 实现思路和{@link ImageCodeValidateFilter}类似，但是我们的登录地址是不同的，否则我们不能判断是验证码登录还是手机验证码登录。</p>
 * @author: stars
 * @data: 2020年 10月 01日 22:06
 **/
@Component
public class MobileValidateFilter extends OncePerRequestFilter {

    /**
     * 异常处理类
     */
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 判断 请求是否为手机登录，且post请求
        if("/mobile/form".equals(request.getRequestURI())
                && "post".equalsIgnoreCase(request.getMethod())) {
            try {
                // 校验验证码合法性
                validate(request);
            }catch (AuthenticationException e) {
                // 交给失败处理器进行处理异常
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                // 一定要记得结束
                return;
            }
        }
        // 放行
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) {
        // 这是发送给用户的短信验证码，当然这一份肯定是保存到Redis中的。
        String sessionCode = "u390";
        // 获取用户输入的验证码
        String inpuCode = request.getParameter("code");
        // 判断是否正确
        if(StringUtils.isBlank(inpuCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        if(!inpuCode.equalsIgnoreCase(sessionCode)) {
            throw new ValidateCodeException("验证码输入错误");
        }
    }
}
