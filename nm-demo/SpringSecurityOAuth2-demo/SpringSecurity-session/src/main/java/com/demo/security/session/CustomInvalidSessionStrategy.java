package com.demo.security.session;

import com.alibaba.fastjson.JSON;
import com.centre.common.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session失效处理逻辑
 * <p>因为登录成功，浏览器的session哪个地方是保存session的，如果失效了，需要向哪个session删除。</p>
 * @author: stars
 * @data: 2020年 10月 02日 12:20
 **/
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {

    private SessionRegistry sessionRegistry;

    public CustomInvalidSessionStrategy(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("getSession().getId(): " + request.getSession().getId());
        System.out.println("getRequestedSessionId(): " + request.getRequestedSessionId());
        // 将浏览器的sessionid清除，不关闭浏览器cookie不会被删除，一直请求都提示：Session失效
        // request.getRequestedSessionId() 获取客户端指定的cookieID
        sessionRegistry.removeSessionInformation(request.getRequestedSessionId());

        // 要将浏览器中的cookie的jsessionid删除
        cancelCookie(request, response);
        // 该出提示
        Result<Object> result = Result.failed(HttpStatus.UNAUTHORIZED.value(), "登录超时，请重新登录!!!");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(result));
    }

    /***
     * 参考记住我功能的 AbstractRememberMeServices 代码
     * @param request
     * @param response
     */
    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("JSESSIONID", null);
        // cookie有效期，0表示会删除，负数为永不过期，但是关闭浏览器会删除的。正直表示多少秒过期。
        cookie.setMaxAge(0);
        // 返回cookis的路径
        cookie.setPath(getCookiePath(request));
        response.addCookie(cookie);
    }

    /***
     * 获取cookie的返回路径
     * @param request
     * @return
     */
    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
}

