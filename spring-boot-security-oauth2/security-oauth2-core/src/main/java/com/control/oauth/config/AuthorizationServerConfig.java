package com.control.oauth.config;

import com.control.oauth.granter.OpenIdGranter;
import com.control.oauth.granter.PwdImgCodeGranter;
import com.control.oauth.properties.SecurityProperties;
import com.control.oauth.service.impl.RedisAuthorizationCodeServices;
import com.control.oauth.service.impl.RedisClientDetailsService;
import com.control.oauth.store.CustomRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 * 认证服务器配置
 * <p>@EnableAuthorizationServer 开启 OAuth2 认证服务器功能</p>
 * @author: stars
 * @data: 2020年 10月 05日 12:48
 **/
@Configuration
@EnableAuthorizationServer
@AutoConfigureAfter(AuthorizationServerEndpointsConfigurer.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 引入AuthenticationManager 认证管理器
     * <p>在{@link SpringSecurityConfig}中注入的。</p>
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 动态认证用户
     */
    @Autowired
    private UserDetailsService userDetailsService ;

    /**
     * token管理方式
     * <p>
     *     在{@link CustomRedisTokenStore}类中已对添加到容器中了。
     * </p>
     */
    @Autowired
    private TokenStore tokenStore;

    /***
     * 授权码管理，自定义了保存到redis中
     * @see RedisAuthorizationCodeServices
     */
    @Autowired(required = false)
    private RandomValueAuthorizationCodeServices authorizationCodeServices;

    /***
     * redis管理客户端
     * <p>注意：这里是基于JDBC来做的</p>
     * @see RedisClientDetailsService
     */
    @Autowired
    private RedisClientDetailsService clientDetailsService;

    /***
     * 异常处理
     */
    @Autowired
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    /**
     * 自定义配置类
     */
    @Autowired
    private SecurityProperties securityProperties ;

    /***
     * 数据源
     * <p>
     *     如果直接使用JDBC管理客户端，需要注入数据源。
     * </p>
     */
    @Autowired
    private DataSource dataSource ;

    /***
     * 自定义身份认证实现类
     * <p>
     *     目前扩展了图形验证码，手机验证码，第三方openId验证
     *     这些认证都统一在{@link TokenGranterConfig}添加到TokenGranter里的。
     * </p>目前扩展了
     * @see com.control.oauth.granter.SMSCodeTokenGranter
     * @see OpenIdGranter
     * @see PwdImgCodeGranter
     * @see TokenGranterConfig
     */
    @Autowired
    private TokenGranter tokenGranter;

    /**
     * 配置被允许访问此认证服务器的客户端详情信息
     * 方式1：内存方式管理
     * 方式2：数据库管理
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用内存方式
       /* clients.inMemory()
                // 客户端id
                .withClient("sse-pc")
                // 客户端密码，要加密,不然一直要求登录, 获取不到令牌, 而且一定不能被泄露
                .secret(passwordEncoder.encode("123456"))
                // 资源id, 如商品资源
                .resourceIds("product-server")
                // 授权类型, 可同时支持多种授权类型
                .authorizedGrantTypes("authorization_code", "password","password_code",
                        "implicit","client_credentials","refresh_token","mobile_password")
                // 授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
                .scopes("all")
                // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码，
                .autoApprove(false)
                // 客户端回调地址
                .redirectUris("https://www.baidu.com/")
                // 访问token的有效时间，默认为 12小时，单位（秒），这里是2小时。
                .accessTokenValiditySeconds(60 * 60 * 2)
                // 刷新令牌，默认为两个月 单位（秒）
                .refreshTokenValiditySeconds(60 * 60 * 24 * 30);*/
        clients.withClientDetails(clientDetailsService);
        // 将数据库中 oauth_client_details 表的信息同步到redis中
        clientDetailsService.loadAllClientToCache();

        // 使用JDBC管理客户端数据
//        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    /**
     * 配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                // // 刷新令牌获取新令牌时需要
                .userDetailsService(userDetailsService)
                // 指定授权码保存方式
                .authorizationCodeServices(authorizationCodeServices)
                // 异常处理
                .exceptionTranslator(webResponseExceptionTranslator)
                // 自定义授权模式
                .tokenGranter(tokenGranter);
        // 设置令牌的默认属性
//        setDefaultToken(endpoints);
    }

    /**
     * 对应于配置AuthorizationServer安全认证的相关信息，创建ClientCredentialsTokenEndpointFilter核心过滤器
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()");
                //让/oauth/token支持client_id以及client_secret作登录认证
//                .allowFormAuthenticationForClients();
    }


    /***
     * 设置令牌的默认属性
     * @param endpoints
     */
    @Deprecated
    private void setDefaultToken(AuthorizationServerEndpointsConfigurer endpoints){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 令牌存储的持久化策略。
        tokenServices.setTokenStore(tokenStore);
        // 是否支持刷新令牌
        tokenServices.setSupportRefreshToken(securityProperties.getAuth().getRenew().getSupportRefreshToken());
        // 用于查找客户端的客户端详细信息服务（如果需要）。如果通过setAccessTokenValiditySeconds（int）全局设置访问令牌到期，则为可选。
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        // 一种访问令牌增强器，在将新令牌保存到令牌存储中之前，它将应用于该令牌
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        // 访问令牌的有效期
        tokenServices.setAccessTokenValiditySeconds(securityProperties.getAuth().getRenew().getAccessTokenValiditySeconds());
        // 刷新令牌的有效期
        tokenServices.setRefreshTokenValiditySeconds(securityProperties.getAuth().getRenew().getRefreshTokenValiditySeconds());
        endpoints.tokenServices(tokenServices);
    }
}
