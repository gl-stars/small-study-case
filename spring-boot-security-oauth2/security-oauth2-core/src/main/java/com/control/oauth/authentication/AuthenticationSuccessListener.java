//package com.control.oauth.authentication;
//
//import org.springframework.security.core.Authentication;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 这个接口是用来监听认证成功之后的处理，也就是说认证成功让成功处理器调用此接口方法 successListener
// * @author: stars
// * @data: 2020年 10月 03日 15:57
// **/
//public interface AuthenticationSuccessListener {
//
//    /***
//     * 查询用户所拥有的权限菜单
//     * @param request
//     * @param response
//     * @param authentication 当前用户信息
//     */
//    void successListener(HttpServletRequest request,
//                         HttpServletResponse response,
//                         Authentication authentication);
//}
