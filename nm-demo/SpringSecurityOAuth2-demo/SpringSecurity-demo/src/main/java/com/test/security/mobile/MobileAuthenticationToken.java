package com.test.security.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/***
 * 封装手机认证Token MobileAuthenticationToken
 * @author stars
 */
public class MobileAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    /**
     * 认证前是手机号码,认证后是用户信息
     */
    private final Object principal;

    /**
     * 认证之前使用的构造 方法, 此方法会标识未认证
     *
     */
    public MobileAuthenticationToken(Object principal) {
        super(null);
        // 手机号码
        this.principal = principal;
        // 未认证
        setAuthenticated(false);
    }

    /**
     * 认证通过后,会重新创建MobileAuthenticationToken实例 ,来进行封装认证信息
     * @param principal 用户信息
     * @param authorities 权限资源
     */
    public MobileAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        // 用户信息
        this.principal = principal;
        // 标识为认证通过
        super.setAuthenticated(true);
    }

    /**
     * 因为它是父类中的抽象方法,,所以要实现,这个方法表示密码，这里不需要密码，直接返回null即可
     * @return
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * 手机号
     * @return
     */
    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}