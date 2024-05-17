package com.way.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser  implements UserDetails {

    private User user;

    /**
     * 这个方法用来返回当前用户的角色/权限信息
     *
     * 在 Spring Security 中，无论是用户角色，还是用户权限，都是从这个方法返回的
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 获取用户密码
     * @return
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 获取用户名
     * @return
     */
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * 账户是否没有过期
     *
     * 正常来说，数据库中应该也有一个描述账户是否过期的字段
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    /**
     * 账户是否没有被锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否没有过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否可用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
