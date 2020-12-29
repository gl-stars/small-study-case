package com.demo.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.user.model.SysRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息
 * @author: stars
 * @date 2020年 07月 09日 11:56
 **/
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
}