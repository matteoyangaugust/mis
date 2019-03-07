package com.matt.bean;

import com.matt.model.Component;
import com.matt.model.Product;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductBean extends Product {

    private List<ComponentBean> components;

    private boolean componentsActive = true;

    public ProductBean(){
        components = new ArrayList<>();
    }

    public List<ComponentBean> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentBean> components) {
        this.components = components;
    }

    public boolean isComponentsActive() {
        return componentsActive;
    }

    public void setComponentsActive(boolean componentsActive) {
        this.componentsActive = componentsActive;
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
