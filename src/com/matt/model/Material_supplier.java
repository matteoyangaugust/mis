package com.matt.model;

import net.sf.json.JSONObject;

public class Material_supplier {
    private Integer sn;
    private String name;

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
