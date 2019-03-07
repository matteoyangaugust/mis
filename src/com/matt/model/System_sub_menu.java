package com.matt.model;

import net.sf.json.JSONObject;

public class System_sub_menu {
    public static final Integer DEFAULT = 1;
    private Integer sn;
    private Integer main_menu_sn;
    private String name;
    private String url;
    private String roles;
    private Integer is_default;

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public Integer getMain_menu_sn() {
        return main_menu_sn;
    }

    public void setMain_menu_sn(Integer main_menu_sn) {
        this.main_menu_sn = main_menu_sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getIs_default() {
        return is_default;
    }

    public void setIs_default(Integer is_default) {
        this.is_default = is_default;
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
