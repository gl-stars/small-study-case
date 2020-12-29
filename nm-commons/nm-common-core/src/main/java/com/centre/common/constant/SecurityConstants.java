package com.centre.common.constant;

/**
 * Security 权限常量
 * @author: stars
 * @data: 2020年 08月 11日 21:54
 **/
public interface SecurityConstants {

    /************** 已上与请求相关 ****************/
    /**
     * 用户信息分隔符
     */
    String USER_SPLIT = ":";

    /**
     * 用户信息头
     */
    String USER_HEADER = "x-user-header";

    /**
     * 用户id信息头
     */
    String USER_ID_HEADER = "x-userid-header";

    /**
     * 角色信息头
     */
    String ROLE_HEADER = "x-role-header";

    /**
     * token请求头名称
     */
    String TOKEN_HEADER = "Authorization";


    /***********    验证码相关   **************/


    /**
     * 默认生成验证码过期时间
     */
    int DEFAULT_IMAGE_EXPIRE = 60;

    /**
     * 验证码
     */
    String CHECK_VALIDCODE = "validCode";

    /**
     * 默认保存code的前缀（前端生成的机械码或者手机号拼接成上传redis的key值）
     */
    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY";

    /****************       登录相关    **********************/
    /**
     * 基础角色
     */
    String BASE_ROLE = "ROLE_USER";


    /**
     * 登出URL
     */
    String LOGOUT_URL = "/oauth/remove/token";

    /********************  redis或者缓存相关  ********************/
    /**
     * OAUTH模式登录处理地址
     */
    String OAUTH_LOGIN_PRO_URL = "/user/login";

    /**
     * redis中授权token对应的key
     */
    String REDIS_TOKEN_AUTH = "auth:";
    /**
     * redis中应用对应的token集合的key
     */
    String REDIS_CLIENT_ID_TO_ACCESS = "client_id_to_access:";
    /**
     * redis中用户名对应的token集合的key
     */
    String REDIS_UNAME_TO_ACCESS = "uname_to_access:";

    /**
     * 缓存client的redis key，这里是hash结构存储
     */
    String CACHE_CLIENT_KEY = "oauth_client_details";

}
