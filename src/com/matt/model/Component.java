package com.matt.model;

import net.sf.json.JSONObject;

public class Component {
    private Integer sn;
    private Integer process_category_sn;
    private String identifier;
    private String name;
    private Integer process_duration;
    private Integer process_yield;
    private Integer remain;
    private Double purchase_fee;
    private Integer active;

    public Double getPurchase_fee() {
        return purchase_fee;
    }

    public void setPurchase_fee(Double purchase_fee) {
        this.purchase_fee = purchase_fee;
    }

    public Integer getProcess_yield() {
        return process_yield;
    }

    public void setProcess_yield(Integer process_yield) {
        this.process_yield = process_yield;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public Integer getProcess_category_sn() {
        return process_category_sn;
    }

    public void setProcess_category_sn(Integer process_category_sn) {
        this.process_category_sn = process_category_sn;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProcess_duration() {
        return process_duration;
    }

    public void setProcess_duration(Integer process_duration) {
        this.process_duration = process_duration;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
