package com.client.oauth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 资源服务器配置
 * @author: stars
 * @data: 2020年 10月 08日 15:09
 **/
@Setter
@Getter
@ConfigurationProperties(prefix = "nm.resource")
public class ResourceProperties {

    /**
     * 资源名称
     */
    private String resourceName = "product-server";
}
