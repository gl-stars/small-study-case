package com.client.oauth.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: stars
 * @data: 2020年 10月 08日 15:09
 **/
@Setter
@Getter
@ConfigurationProperties(prefix = "security.oauth2")
public class PermitUrlProperties {

    /**
     * 这里的地址需要重新写，因为不写资源服务器这里就不能访问，认证服务器开放，只能说明这几个路径在认证服务器里面开放出来，
     * 但是资源服务器里面还是不可以访问的。
     */
    private static final String[] ENDPOINTS = {
            "/**/validata/code/**",
            "/**/validata/smsCode/**"
    };

    private String[] ignored;

    /**
     * 需要放开权限的url
     *
     * @return 自定义的url和监控中心需要访问的url集合
     */
    public String[] getIgnored() {
        if (ignored == null || ignored.length == 0) {
            return ENDPOINTS;
        }

        List<String> list = new ArrayList<>();
        for (String url : ENDPOINTS) {
            list.add(url);
        }
        for (String url : ignored) {
            list.add(url);
        }

        return list.toArray(new String[list.size()]);
    }

    public void setIgnored(String[] ignored) {
        this.ignored = ignored;
    }

}
