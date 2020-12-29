//package com.control.oauth.authentication;
//
//import com.alibaba.fastjson.JSON;
//import com.centre.common.model.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 成功处理类
// * @author: stars
// * @data: 2020年 10月 03日 15:55
// **/
//@Component("customAuthenticationSuccessHandler")
//public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
//    /***
//     * 容器中可以不需要有接口的实现，如果有则自动注入
//     * <p>其实就是对用户的权限、角色等信息做了一个过滤。今后如果是URL级别的，
//     * 这里的过滤还需要做调整，这里已经把URL过滤了，所以这里是没有URL数据的。</p>
//     */
//    @Autowired(required = false)
//    private AuthenticationSuccessListener authenticationSuccessListener;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//
//        if (authenticationSuccessListener != null) {
//            // 当认证之后 ，调用此监听，进行后续处理，比如加载用户权限菜单
//            authenticationSuccessListener.successListener(request, response, authentication);
//        }
//        // 认证成功后，响应JSON字符串
//        Result result = Result.succeed("认证成功");
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().write(JSON.toJSONString(result));
//    }
//
//}
//
