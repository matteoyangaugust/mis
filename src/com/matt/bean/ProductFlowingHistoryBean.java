package com.matt.bean;

import com.matt.model.Component;
import com.matt.model.Product;
import com.matt.model.Product_flowing_history;
import net.sf.json.JSONObject;

import java.util.List;

public class ProductFlowingHistoryBean extends Product_flowing_history {
    private Product product;
    private String updateTimeWithUnit;


    public String getUpdateTimeWithUnit() {
        return updateTimeWithUnit;
    }

    public void setUpdateTimeWithUnit(String updateTimeWithUnit) {
        this.updateTimeWithUnit = updateTimeWithUnit;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
