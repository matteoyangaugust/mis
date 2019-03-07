package com.matt.model;

import net.sf.json.JSONObject;

public class Process_category {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MOLDING = "ROLE_MOLDING";
    public static final String ROLE_PURCHASE = "ROLE_PURCHASE";
    public static final String ROLE_RUBBER = "ROLE_RUBBER";
    public static final String ROLE_STAMPING = "ROLE_STAMPING";
    private Integer sn;
    private String name;
    private String relation;


    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
