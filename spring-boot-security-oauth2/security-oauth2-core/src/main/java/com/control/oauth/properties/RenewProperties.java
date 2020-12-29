package com.control.oauth.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 续签配置
 * @author: stars
 * @data: 2020年 10月 05日 12:50
 **/
@Setter
@Getter
public class RenewProperties {
    /**
     * 是否开启token自动续签（目前只有redis实现）
     */
    private Boolean enable = true;

    /**
     * 续签时间比例，当前剩余时间小于小于过期总时长的50%则续签
     */
    private Double timeRatio = 0.5;

    /***
     * 访问令牌有效时间 30 分钟（单位秒）
     * <p>
     *     60 * 60 * 12 默认为 12 小时
     * </p>
     */
    private Integer accessTokenValiditySeconds = 60 * 30;

    /****
     * 刷新令牌有效时间 1 个小时（单位秒）
     * <p>
     *     60 * 60 * 24 * 30 默认为 30 天
     * </p>
     */
    private Integer refreshTokenValiditySeconds = 60 * 60 ;

    /***
     * 是否支持刷新令牌
     * false : 不支持
     */
    private Boolean supportRefreshToken = false ;
}
