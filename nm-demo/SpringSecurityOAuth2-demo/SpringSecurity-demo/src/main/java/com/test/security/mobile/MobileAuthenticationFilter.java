package com.test.security.mobile;


import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 实现手机认证过滤器
 * <p>模仿 {@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}</p>
 * @author: stars
 * @data: 2020年 10月 01日 22:10
 **/
public class MobileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 手机号名称，今后需要根据这个名称从数据库中获取手机号
     */
    private String mobileParameter = "mobile";
    private boolean postOnly = true;

    /***
     * 将登陆地址和登陆方式注入
     */
    public MobileAuthenticationFilter() {
        super(new AntPathRequestMatcher("/mobile/form", "POST"));
    }

    /**
     * 执行实际的认证。
     * <p>
     * 1.实施应做到以下几点之一：
     * 2.返回令牌用于身份验证的用户填充的认证，表明身份验证成功
     * 3.返回null，表明身份验证过程仍在进行中。 返回前，实施中应执行完成这一过程所需的任何额外的工作。
     * 4.如果在验证过程失败，抛出的AuthenticationException
     * </p>
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        // 判断是否为post提交方式
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        // 获取手机号
        String mobile = obtainMobile(request);
        // 判断手机号是否为null
        if (mobile == null) {
            mobile = "";
        }
        // 将手机号两边去除空格
        mobile = mobile.trim();
        // 将该手机号标识为未认证状态
        MobileAuthenticationToken authRequest = new MobileAuthenticationToken(mobile);
        // 将 sessionID和hostname添加 到MobileAuthenticationToken
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * 从请求中获取手机号码
     */
    @Nullable
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    /**
     * 将 sessionID和hostname添加 到MobileAuthenticationToken
     */
    protected void setDetails(HttpServletRequest request,
                              MobileAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    /**
     * 设置是否为post请求
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public String getMobileParameter() {
        return mobileParameter;
    }

    public void setMobileParameter(String mobileParameter) {
        this.mobileParameter = mobileParameter;
    }
}
