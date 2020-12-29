package com.client.oauth.config;

import com.client.oauth.properties.ResourceProperties;
import com.client.oauth.props.PermitUrlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 资源服务器相关配置类
 * <p>@EnableResourceServer 标识为资源服务器，请求服务中的资源，就要带着token过来，找不到token或token是无效访问不了资源</p>
 * <p>特别强调：设置了资源服务器以后，授权码模式就访问不了。但是没有资源服务器，即使拿到token也访问不了接口。需要作出相应的处理。</p>
 * @author: stars
 * @data: 2020年 10月 05日 12:48
 **/
@Configuration
@EnableResourceServer
@SuppressWarnings("all")
@AutoConfigureAfter(TokenStore.class)
@EnableConfigurationProperties({ResourceProperties.class, PermitUrlProperties.class})
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    /**
     * 令牌管理策略，使用哪种类型认证。例如：内存方式还是redis，JDBC方式。
     * <p>例如使用redis保存认证信息，在配置文件的时候就需要将{@link TokenStore}这个实力注册到容器中。配置如下：
     * <pre>
     *     @Bean
     *      public TokenStore tokenStore() {
     *          Redis 管理令牌
     *          return new RedisTokenStore(redisConnectionFactory);
     *      }
     * </pre>
     * 使用redis存储认证信息，需要注入{@link org.springframework.data.redis.connection.RedisConnectionFactory}，如果保存信息是自己创建
     * </p>
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * 获取资源名称
     */
    @Autowired
    private ResourceProperties resourceServerProperties;

    /**
     * 自定义配置
     */
    @Autowired
    private PermitUrlProperties permitUrlProperties;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;
    @Autowired
    private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;

    /****
     * 添加特定于资源服务器的属性（如资源id）。默认值应该适用于许多应用程序，但您可能需要至少更改资源id
     * @param resources 资源–资源服务器的配置器
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 当前资源服务器的资源id，认证服务会认证客户端有没有访问这个资源id的权限，有则可以访问当前服务
        resources.resourceId(resourceServerProperties.getResourceName())
                .tokenStore(tokenStore);
        resources.stateless(true);
        // 自定义异常处理端口
        resources.authenticationEntryPoint(authenticationEntryPoint);
        resources.expressionHandler(expressionHandler);
        resources.accessDeniedHandler(oAuth2AccessDeniedHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().disable();
        // 这个是不认证，所有的请求都不认证
//        http.authorizeRequests().anyRequest().permitAll();
        // 强制认证
        http.authorizeRequests()
                // 指定范围访问该资源 (指定一个)
//                .antMatchers("/**").access("#oauth2.hasScope('all')");
                // 指定多个
//                .antMatchers("/**").access("#oauth2.hasAnyScope('all','access')")
                // 指定不用认证的路径，虽然这几个路径在认证服务器里面配置过了，但是资源服务器这里没有配置，就意味着这里还是进不来。
//                .antMatchers(permitUrlProperties.getIgnored()).permitAll()
                // 其余的路径全部认证
//                .anyRequest().authenticated();
                // 注意：这里一定要全部放行，否则在这里就被拦截了，无法去认证服务器。所以一直报需要认证的错误。
                .anyRequest().permitAll();

    }
}
