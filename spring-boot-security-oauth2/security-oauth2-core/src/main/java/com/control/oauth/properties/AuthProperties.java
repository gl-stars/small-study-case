package com.control.oauth.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 认证配置
 *
 * @author: stars
 * @data: 2020年 10月 05日 12:50
 **/
@Setter
@Getter
public class AuthProperties {

    /***
     * token管理方式
     * <ul>
     *     <li>db：数据库管理方式</li>
     *     <li>authJwt：JWT方式</li>
     *     <li>redis：redis方式</li>
     * </ul>
     */
    private String type ;

    /**
     * token自动续签配置（目前只有redis实现）
     */
    private RenewProperties renew = new RenewProperties();
}
