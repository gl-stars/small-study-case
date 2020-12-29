package com.control.oauth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 配置类
 * <p>将所有有关权限认证方面的配置，全部归纳在这里。</p>
 * @author: stars
 * @data: 2020年 10月 05日 12:50
 **/
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "nm.security")
public class SecurityProperties {

    /**
     * 认证相关配置
     */
    private AuthProperties auth = new AuthProperties();

    /***
     * 不需要登录即可访问的路径
     */
    private PermitProperties permit = new PermitProperties();
}
