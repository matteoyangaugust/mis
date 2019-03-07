package com.matt.bean;

import com.matt.model.System_main_menu;
import com.matt.model.System_sub_menu;
import com.matt.model.System_user;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class CustomUser implements UserDetails{
    private static final long serialVersionUID = 1L;
    private  User user;
    private System_user userInfo;
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomUser(final User _user, System_user userInfo) {
        this.user = _user;
        this.userInfo = userInfo;
    }

    public System_user getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(System_user userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        if (this.user == null) {
            return null;
        }
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "\nCustomUser{" +
                "user=" + user +
                ", name='" + name + '\'' +
                '}';
    }
}
