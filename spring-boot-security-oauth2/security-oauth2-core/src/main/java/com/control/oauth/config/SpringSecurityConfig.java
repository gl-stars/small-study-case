package com.control.oauth.config;

import com.control.oauth.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全配置类
 * <p>
 *     整个框架的思路莫非就亮点最终要
 *     1、获取信息
 *     2、处理信息
 * 获取信息，就是获取验证码、校验码、用户名和密码等。
 * 处理信息就是对用户名和密码校验一下，是否匹配等。
 * <b>那么获取信息怎么获取呢？因为这个框架是通过过滤链的方式过滤信息的了。那么那些信息需要通过过滤器过滤出来处理，怎么处理。这就解决这个框架中最核心的两个问题，身份认证和权限校验</b>
 *  @EnableWebSecurity已经包含 @Configuration注解
 *  </p>
 * @author: stars
 * @data: 2020年 10月 05日 12:50
 **/
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /****
     * 动态认证用户
     */
//    @Autowired
//    private NmUserDetailsService userDetailsService ;

    /***
     * 自定义配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 认证管理器：
     * 1、认证信息提供方式（用户名、密码、当前用户的资源权限）
     * 2、可采用内存存储方式，也可能采用数据库方式等
     * @param auth
     * @throws Exception
     */
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 动态认证用户
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }*/

    /***
     * 资源权限配置（过滤链）
     * 1、被拦截的资源
     * 2、资源所对应的角色权限
     * 3、定义认证方式：httpBasic 、httpForm
     * 4、定制登录页面、登录请求地址、错误处理方式
     * 5、自定义 spring security 过滤器
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 添加验证码验证
        http.formLogin()
//                .loginProcessingUrl("/login/form") // 登录表单提交处理Url, 默认是 /login
//                .usernameParameter("name") // 默认用户名的属性名是 username
//                .passwordParameter("pwd") // 默认密码的属性名是 password
                .and()
                // 授权请求
                .authorizeRequests()
                // .antMatchers(release())指定访问路径 。//.permitAll() 表示里面的所有请求都可以访问。
                .antMatchers(securityProperties.getPermit().getURL()).permitAll()
                // .anyRequest() 表示所的请求，.authenticated();表示都需要认证才可以访问，但是除了 org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry.antMatchers(java.lang.String...) 里面封装的请求。
                .anyRequest()
                .authenticated();
    }

    /**
     * 密码模式需要用到这个认证管理器
     * <p>
     *     <b>如果这里不注入，那么密码模式的 {@link AuthorizationServerConfig}类中的 {@code configure(AuthorizationServerEndpointsConfigurer endpoints)}
     *     方法中authenticationManager(authenticationManager)找不到 authenticationManager认证管理器。
     *     中</b>
     *     这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     * </p>
     * @return 认证管理对象
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
