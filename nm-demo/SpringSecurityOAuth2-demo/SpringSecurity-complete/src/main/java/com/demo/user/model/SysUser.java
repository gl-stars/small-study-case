package com.demo.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.Data;
import org.assertj.core.util.Lists;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户信息
 * @author: stars
 * @date 2020年 07月 09日 11:45
 **/
@Data
public class SysUser implements UserDetails {

    @TableId(type = IdType.AUTO) // 表示主键自增长
    private Long id;

    private String username;
    /**
     * 密码需要通过加密后存储
     */
    private String password;
    /**
     * 帐户是否过期(true(1) 未过期，false(0)已过期)
     * 设置默认值为true，新增用户默认未过期
     *
     * 注意：生成的setter和getter方法没有 `is`
     * setAccountNonExpired
     * getAccountNonExpired
     * 所以前端获取时也不要有 `is`
     */
    private boolean isAccountNonExpired = true;

    /**
     * 帐户是否被锁定(true(1) 未过期，false(0)已过期)
     * 设置默认值为true，新增用户默认未过期
     */
    private boolean isAccountNonLocked = true;

    /**
     * 密码是否过期(true(1) 未过期，false(0)已过期)
     * 设置默认值为true，新增用户默认未过期
     */
    private boolean isCredentialsNonExpired = true;

    /**
     * 帐户是否可用(true(1) 可用，false(0)未删除)
     * 设置默认值为true，新增用户默认未过期
     */
    private boolean isEnabled = true;

    /**
     * 它不是sys_user表中的属性，所以要进行标识，不然mybatis-plus会报错
     */
    @TableField(exist = false)
    private Collection<? extends GrantedAuthority> authorities;

    private String nickName;
    private String mobile;
    private String email;
    private Date createDate;
    private Date updateDate;


    /**
     * 拥有角色集合
     */
    @TableField(exist = false)
    private List<SysRole> roleList = Lists.newArrayList();
    /**
     * 获取所有角色id
     */
    @TableField(exist = false)
    private List<Long> roleIds = Lists.newArrayList();
    public List<Long> getRoleIds() {
        if(CollectionUtils.isNotEmpty(roleList)) {
            roleIds = Lists.newArrayList();
            for(SysRole role : roleList) {
                roleIds.add(role.getId());
            }
        }
        return roleIds;
    }

    /**
     * 封装当前用户拥有的权限资源对象
     */
    @TableField(exist = false)
    private List<SysPermission> permissions = Lists.newArrayList();


}
