package com.matt.model;

import net.sf.json.JSONObject;

public class Elements_of_component {
    public static final Integer MATERIAL_TYPE = 1;
    public static final Integer COMPONENT_TYPE = 2;
    private Integer sn;
    private Integer element_sn;
    private Integer component_sn;
    private Double element_amount;
    private Integer elements_type;

    public Integer getElements_type() {
        return elements_type;
    }

    public void setElements_type(Integer elements_type) {
        this.elements_type = elements_type;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public Integer getElement_sn() {
        return element_sn;
    }

    public void setElement_sn(Integer element_sn) {
        this.element_sn = element_sn;
    }

    public Integer getComponent_sn() {
        return component_sn;
    }

    public void setComponent_sn(Integer component_sn) {
        this.component_sn = component_sn;
    }

    public Double getElement_amount() {
        return element_amount;
    }

    public void setElement_amount(Double element_amount) {
        this.element_amount = element_amount;
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
