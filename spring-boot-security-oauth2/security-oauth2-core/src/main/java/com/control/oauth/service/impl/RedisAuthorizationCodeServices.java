package com.control.oauth.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis管理授权码
 * <p>授权码默认是保存在内存中的，但是这种方式合适单机，如果分布式服务就不行了。默认调用的是 {@link InMemoryAuthorizationCodeServices}实例
 * 但是它还提供JDBC管理，保存到数据库的 oauth_code 表中，这里我们就心键一个类，将授权码
 * 保存到redis，这个比较实用。所以数据库中的 oauth_code 表就不需要了。
 * <b>保存的默认时间为 10分钟，在这里实现 {@link RedisAuthorizationCodeServices#store(java.lang.String, org.springframework.security.oauth2.provider.OAuth2Authentication)}</b></p>
 * JdbcAuthorizationCodeServices替换
 * @author: stars
 * @data: 2020年 10月 06日 14:37
 **/
@Service
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {
    private RedisTemplate<String, Object> redisTemplate;

    public RedisAuthorizationCodeServices(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 生成随机值授权码的授权码服务的基本实现
     * 替换JdbcAuthorizationCodeServices的存储策略
     * 将存储code到redis，并设置过期时间，10分钟
     */
    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        redisTemplate.opsForValue().set(redisKey(code), authentication, 10, TimeUnit.MINUTES);
    }

    /***
     * 删除授权码
     * @param code
     * @return
     */
    @Override
    protected OAuth2Authentication remove(final String code) {
        String codeKey = redisKey(code);
        OAuth2Authentication token = (OAuth2Authentication) redisTemplate.opsForValue().get(codeKey);
        this.redisTemplate.delete(codeKey);
        return token;
    }

    /**
     * redis中 code key的前缀
     *
     * @param code
     * @return
     */
    private String redisKey(String code) {
        return "oauth:code:" + code;
    }
}
