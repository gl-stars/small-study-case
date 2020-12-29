//package com.control.oauth.authentication;
//
//import com.control.oauth.user.model.SysUserDO;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 当认证成功后，会触发此实现类方法 successListener
// * @author: stars
// * @data: 2020年 10月 03日 15:57
// **/
//@Component
//public class MenuAuthenticationSuccessListener implements AuthenticationSuccessListener {
//
//    /**
//     * 查询用户所拥有的权限菜单
//     * @param request
//     * @param response
//     * @param authentication 当用户认证通过后，会将认证对象传入
//     */
//    @Override
//    public void successListener(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        System.out.println("查询用户所拥有的权限菜单: " + authentication);
//        Object principal = authentication.getPrincipal();
//        if(principal != null && principal instanceof SysUserDO) {
//            SysUserDO sysUser = (SysUserDO) principal;
//            loadMenuTree(sysUser);
//        }
//
//        Object newPrincipal = authentication.getPrincipal();
//        // #authentication.principal.permissions
//        System.out.println("newPrincipal: " + newPrincipal);
//    }
//
//    /**
//     * 只加载菜单 ，不需要按钮
//     * @param sysUser
//     */
//    public void loadMenuTree(SysUserDO sysUser) {
//        // 保存用户的权限和角色等信息
//    }
//}
