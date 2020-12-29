//package com.demo.security.authorize;
//
//import org.assertj.core.util.Lists;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * 身份认证相关的授权配置
// * <p>配置不许要访问的路径</p>
// * @author stars
// */
//@Component
//@Order(Integer.MAX_VALUE) // 值越小加载越优先，值越大加载越靠后
//public class CustomAuthorizeConfigurerProvider implements AuthorizeConfigurerProvider {
//
//    @Override
//    public void confiure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
//        // 添加不需要访问的路径
//        config.antMatchers(release()).permitAll();
//        // 其他请求都要通过身份认证
//        config.anyRequest().authenticated();
//    }
//    /***
//     * 这里返回不用认证的URL，正式项目中可以做成在配置文件中配置，或者通过数据库来管理。
//     * @return
//     */
//    private String[] release(){
//        // 一般数据库查询出来都是集合，我们几模拟真实的情况。
//        List<String> list = Lists.newArrayList();
//        // 测试不用验证是否通过的
//        list.add("/find/abc");
//        // 获取验证码的
//        list.add("/code/image");
//        String[] aars = new String[list.size()];
//        list.toArray(aars);
//        return aars ;
//    }
//}
