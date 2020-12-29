package com.demo.security.config;

import cn.hutool.core.collection.CollectionUtil;
import com.demo.security.code.ImageCodeValidateFilter;
import com.demo.security.mobile.MobileAuthenticationConfig;
import com.demo.security.mobile.MobileValidateFilter;
import com.demo.security.service.CustomLogoutHandler;
import com.demo.user.model.SysPermission;
import com.demo.user.service.SysPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

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
 * @data: 2020年 10月 03日 15:29
 **/
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /***
     * 加密接口，常用方法如下。
     * <p>
     *     //加密(我们来调用，一般在注册的时候，将前端传过来的密码，进行加密后保存进数据库)
     * String encode(CharSequence rawPassword);
     * //加密前后对比(一般用来比对前端提交过来的密码和数据库存储的加密密码, 也就是明文和密文的对比)
     * boolean matches(CharSequence rawPassword, String encodedPassword);
     * //是否需要再次进行编码增加安全性, 默认不需要
     * default boolean upgradeEncoding(String encodedPassword) {
     * return false;
     * }
     * </p>
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 明文+随机盐值》加密存储
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证成功处理类
     */
    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    /***
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    /**
     * 注入验证码登录配置类
     * <p>将校验过滤器 imageCodeValidateFilter 添加到 UsernamePasswordAuthenticationFilter 前面</p>
     */
    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter ;

    /**
     * 注入手机验证码校验
     */
    @Autowired
    private MobileValidateFilter mobileValidateFilter;

    /**
     * 退出相关处理
     * <p>删除token或者令牌保存在Redis中的，就删除令牌。</p>
     */
    @Autowired
    private CustomLogoutHandler customLogoutHandler;

    /***
     * 权限的相关配置，下面注释的部分被拆分出去了。
     */
//    @Autowired
//    private AuthorizeConfigurerManager authorizeConfigurerManager;

    /***
     * 动态认证用户信息
     */
    @Autowired
    private UserDetailsService customUserDetailsService;

    /***
     * 注入 关于手机登录的组件
     */
    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;

    /**
     * 查询所有权限数据
     */
    @Autowired
    private SysPermissionService sysPermissionService;

    /****
     * 身份认证管理器
     * 所提供的信息是否正确，验证码或者用户名和密码等
     * 1、认证信息提供方式（用户名、密码、当前用户的资源权限）
     * 2、可采用内存存储方式，也可能采用数据库方式等
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 数据库存储的密码必须是加密后的，不然会报错：There is no PasswordEncoder mapped for the id "null"
//        String password = passwordEncoder().encode("123456");
//        auth.inMemoryAuthentication().withUser("admin")
//                .password(password).authorities("ADMIN");
        // 用户信息动态认证
        auth.userDetailsService(customUserDetailsService);
    }

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
//        http.httpBasic() // 采用 httpBasic认证方式
        // 校验手机验证码过滤器
        http.addFilterBefore(mobileValidateFilter, UsernamePasswordAuthenticationFilter.class) // 手机验证码处理类
                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class) //  注入验证码登录配置类
                .formLogin() // 表单登录方式
                .loginProcessingUrl("/login/from") // 登录表单提交处理url, 默认是/login
                .usernameParameter("name") //默认的是 username
                .passwordParameter("pwd")  // 默认的是 password
                .successHandler(customAuthenticationSuccessHandler) // 登录成功处理类
                .failureHandler(customAuthenticationFailureHandler)// 登录失败处理类
//                .and()
//                    .authorizeRequests() // 授权请求
//                    .antMatchers(securityProperties.getAuthentication().getLoginPage(),
////                    "/code/image","/mobile/page", "/code/mobile"
//                            securityProperties.getAuthentication().getImageCodeUrl(),
//                            securityProperties.getAuthentication().getMobilePage(),
//                            securityProperties.getAuthentication().getMobileCodeUrl()
//                    ).permitAll() // 放行/login/page不需要认证可访问
//
//                    // 有 sys:user 权限的可以访问任意请求方式的/role
//                    .antMatchers("/user").hasAuthority("sys:user")
//                    // 有 sys:role 权限的可以访问 get方式的/role
//                    .antMatchers(HttpMethod.GET,"/role").hasAuthority("sys:role")
//                    .antMatchers(HttpMethod.GET, "/permission")
//                    // ADMIN 注意角色会在前面加上前缀 ROLE_ , 也就是完整的是 ROLE_ADMIN, ROLE_ROOT
//                    .access("hasAuthority('sys:premission') or hasAnyRole('ADMIN', 'ROOT')")
//
//                    .anyRequest().authenticated() //所有访问该应用的http请求都要通过身份认证才可以访问
                .and()
                    .logout()
                    .addLogoutHandler(customLogoutHandler) // 退出清除缓存
                    .logoutUrl("/user/logout") // 退出请求路径
//                    .logoutSuccessUrl("/mobile/page") //退出成功后跳转地址
//                    .deleteCookies("JSESSIONID") // 退出后删除什么cookie值
        ;// 注意不要少了分号

        http.csrf().disable(); // 关闭跨站请求伪造
        //将手机认证添加到过滤器链上
        http.apply(mobileAuthenticationConfig);

        // 将所有的授权配置统一的起来，这里使用一个设计模式，其实这完全可以不用，直接将配置写在这个方法中也没问题，但是为了简化，下面定义了customAuthorizeConfigurerProvider()和systemAuthorizeConfigurerProvider()方法完成形相同的问题。
//        authorizeConfigurerManager.configure(http.authorizeRequests());
        systemAuthorizeConfigurerProvider(http);
        // 这个方法一定要放在最后，因为这个方法中具有 .anyRequest().authenticated(); 配置，这个配置一定要在最后执行。
        customAuthorizeConfigurerProvider(http);
    }

    /***
     * 保存不用登录就可以访问的URL
     * <p>这里是使用方法引用的形式，虽然没有看见返回值，但是数据已经更改了。</p>
     * @param http
     * @throws Exception
     */
    private void customAuthorizeConfigurerProvider(HttpSecurity http) throws Exception{
        // 添加不需要访问的路径
        http.authorizeRequests().antMatchers(release()).permitAll()
        // 其他请求都要通过身份认证
        .anyRequest().authenticated();
    }
    /***
     * 这里返回不用认证的URL，正式项目中可以做成在配置文件中配置，或者通过数据库来管理。
     * @return
     */
    private String[] release(){
        // 一般数据库查询出来都是集合，我们几模拟真实的情况。
        List<String> list = Lists.newArrayList();
        // 测试不用验证是否通过的
        list.add("/find/abc");
        // 获取验证码的
        list.add("/code/image");
        String[] aars = new String[list.size()];
        list.toArray(aars);
        return aars ;
    }

    /**
     * 保存权限这些
     * @param http
     * @throws Exception
     */
    private void systemAuthorizeConfigurerProvider(HttpSecurity http) throws Exception {
        // 有 sys:user 权限的可以访问任意请求方式的/role
        // 这里特别注意：如果这里没有指定，那么所有都需要登录才能访问。但是处理设置为不用登录访问的地址意外。
//        http.authorizeRequests().antMatchers("/user").hasAuthority("sys:user")
//                // 有 sys:role 权限的可以访问 get方式的/role
//                .antMatchers(HttpMethod.GET,"/role").hasAuthority("sys:role")
//                .antMatchers(HttpMethod.GET, "/permission")
//                // ADMIN 注意角色会在前面加上前缀 ROLE_ , 也就是完整的是 ROLE_ADMIN, ROLE_ROOT
//                .access("hasAuthority('sys:premission') or hasAnyRole('ADMIN', 'ROOT')");
        /****************   以上为测试数据     *****************/
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();

        List<SysPermission> permissionAll = sysPermissionService.findPermissionAll();
        if (CollectionUtil.isEmpty(permissionAll)) {
            return;
        }
        permissionAll.forEach(per -> {
            if (StringUtils.isNoneBlank(per.getUrl())) {
                // 注意，url地址不能重复，如果重复了，不会报错，但是没有效果，后面是否会替换前面的不太清楚。
                registry.antMatchers(per.getUrl()).hasAnyAuthority(per.getCode());
//                registry.antMatchers("/user").hasAnyAuthority("sys:user");
//                registry.antMatchers("/useraa").hasAnyAuthority("sys:useraa");
//                System.out.println("URL地址："+per.getUrl()+"。角色："+per.getCode());
            }
        });


    }
}
