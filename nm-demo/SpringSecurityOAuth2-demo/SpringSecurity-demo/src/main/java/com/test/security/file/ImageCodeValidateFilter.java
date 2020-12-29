package com.test.security.file;

import com.test.security.authentication.CustomAuthenticationFailureHandler;
import com.test.security.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 实现图形验证码验证登录
 * <p>所有请求之前被调用一次，在这里需要判断，请求的是否为登录URL，如果是，则判断验证码是否正确，验证码不正确是
 * 通过抛出异常的方式，然后在定义一个异常处理类，处理这里抛出来的异常。如果不是就直接放行。
 * 登录地址可以在 {@link AbstractAuthenticationFilterConfigurer#loginProcessingUrl} 配置，默认为 /login</p>
 * @author: stars
 * @data: 2020年 10月 01日 16:24
 **/
@Component("imageCodeValidateFilter")
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /***************    不知道是postman有问题还是怎么，一直获取不到验证码和提交方法，所以这里就模拟真实情况，一下代码不该有。************************/
        try {
            // 校验验证码合法性
            validate(request);
        }catch (AuthenticationException e){
            // 交给失败处理器进行处理异常
            customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
            // 一定要记得结束
            return;
        }
        /***************************************/

        // 判断请求地址是否为登录地址并且提示方式为 post
        if ("/login".equals(request.getRequestURI()) && "post".equalsIgnoreCase(request.getMethod())){
            try {
                // 校验验证码合法性
                validate(request);
            }catch (AuthenticationException e){
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
        // 获取session生成的验证码，这里真是情况是用户传一个机械码过来当做key，然后我们将验证码生成出来当做value存入redis，然后在将验证码图片返回给前端，验证的时候前端将机械码和验证码返回来坐验证。
        String sessionCode = "889900" ;

        // 获取用户输入的验证码
//        String inpuCode = request.getParameter("code");
        /********************************/
//        String inpuCode = null ;
        String inpuCode = "889900" ;
        /********************************/
        // 判断是否正确
        if (StringUtils.isBlank(inpuCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        if (!inpuCode.equalsIgnoreCase(sessionCode)) {
            throw new ValidateCodeException("验证码输入错误");
        }
    }
}
