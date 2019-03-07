package com.matt.bean;

import com.matt.model.Material;
import net.sf.json.JSONObject;

public class ElementsOfComponentBean extends Material {
    private Double element_amount;
    private Integer element_type;

    public Integer getElement_type() {
        return element_type;
    }

    public void setElement_type(Integer element_type) {
        this.element_type = element_type;
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
