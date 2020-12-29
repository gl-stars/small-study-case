package com.demo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.user.mapper.SysUserMapper;
import com.demo.user.model.SysUser;
import com.demo.user.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author: stars
 * @date 2020年 07月 09日 11:53
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 通过手机号查询
     * @param mobile 手机号
     * @return 用户信息
     */
    @Override
    public SysUser findByMobile(String mobile) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper();
        wrapper.eq("username",mobile);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 通过用户名查询
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public SysUser findByUsername(String username) {
        if(StringUtils.isEmpty(username)) {
            return null;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        // baseMapper 对应的是就是 SysUserMapper
        return baseMapper.selectOne(queryWrapper);
    }
}
