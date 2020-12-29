package com.demo.security.authentication;

import com.alibaba.fastjson.JSON;
import com.centre.common.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 失败处理类
 * @author: stars
 * @data: 2020年 10月 03日 16:12
 **/
@Component("customAuthenticationFailureHandler")
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     *
     * @param exception 认证失败时抛出异常
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 认证失败响应JSON字符串，
        Result result = Result.failed(HttpStatus.UNAUTHORIZED.value(),exception.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
