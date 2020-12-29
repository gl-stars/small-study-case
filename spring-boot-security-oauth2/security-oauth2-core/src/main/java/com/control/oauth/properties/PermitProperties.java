package com.control.oauth.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 路径相关配置
 * @author: stars
 * @data: 2020年 10月 07日 17:41
 **/
@Getter
@Setter
public class PermitProperties {

    /**
     * 登录前需要操作的访问路径，不需要登录即可访问。
     */
    private final String[] URL = {
            "/**/validata/code/**",
            "/**/validata/smsCode/**"
    };
}
