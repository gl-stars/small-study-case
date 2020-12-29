package com.demo.security.config;

import com.demo.security.file.ImageCodeValidateFilter;
import com.demo.security.mobile.MobileAuthenticationConfig;
import com.demo.security.mobile.MobileValidateFilter;
import com.demo.security.service.impl.CustomUserDetailsService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.AbstractRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
 *
 * @author: gl_stars
 * @data: 2020年 09月 29日 13:22
 **/
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 动态认证
     */
    @Autowired
    private CustomUserDetailsService userDetailsService ;

    /**
     * 注入验证码登录配置类
     * <p>将校验过滤器 imageCodeValidateFilter 添加到 UsernamePasswordAuthenticationFilter 前面</p>
     */
    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter ;

    /**
     * 注入验证码校验
     */
    @Autowired
    private MobileValidateFilter mobileValidateFilter;

    /***
     * 注入 关于手机登录的组件
     */
    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;

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
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
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
        // 用户信息存储在内存中，这里的密码必须加密保存，权限也必须携带上，可以为集合，但是泛型必须为 GrantedAuthority 子类
//        auth.inMemoryAuthentication().withUser("admin")
//                .password(passwordEncoder().encode("123456")).authorities("ADMIN");
        // 注入动态认证对象
        auth.userDetailsService(userDetailsService);
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
        // 添加验证码验证
        http.addFilterBefore(mobileValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
//                .loginProcessingUrl("/login/form") // 登录表单提交处理Url, 默认是 /login
//                .usernameParameter("name") // 默认用户名的属性名是 username
//                .passwordParameter("pwd") // 默认密码的属性名是 password
                .and()
                // 认证请求
                .authorizeRequests()
                // 指定不拦截的URL
                .antMatchers(release()).permitAll()
                // 使用 formLogin 方式拦截所有http请求。
                .anyRequest()
                .authenticated();
        http.apply(mobileAuthenticationConfig);
    }

    /***
     * 不需要认证的静态资源
     * <p> 不需要认证的地址参考：{@link AbstractRequestMatcherRegistry#antMatchers(java.lang.String...)}
     * 这里虽然是传递一个可变参数，但是有些时候我们为了将这些不用认证的地址使用数据库来保存，动态调整时。
     * 从库中查出来的数据往往是List集合中，但是我们可以转为数组，直接将数组放在这个可变参数中。</p>
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
//        List<String> list = new ArrayList<>();
//        String[] arrs = new String[list.size()];
//        list.toArray(arrs);
//        web.ignoring().antMatchers(arrs);
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
}

