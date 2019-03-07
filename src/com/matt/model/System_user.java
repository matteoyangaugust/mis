package com.matt.model;

import java.util.LinkedHashMap;
import java.util.List;

public class System_user {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    private Integer sn;
    private String username;
    private String password;
    private String name;
    private String role;
    private LinkedHashMap<System_main_menu, List<System_sub_menu>> menus;

    public LinkedHashMap<System_main_menu, List<System_sub_menu>> getMenus() {
        return menus;
    }

    public void setMenus(LinkedHashMap<System_main_menu, List<System_sub_menu>> menus) {
        this.menus = menus;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "\nSystem_user{" +
                "sn=" + sn +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", menus=" + menus +
                '}';
    }
}
