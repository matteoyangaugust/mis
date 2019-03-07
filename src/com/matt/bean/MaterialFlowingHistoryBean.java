package com.matt.bean;

import com.matt.model.Material;
import com.matt.model.Material_flowing_history;
import com.matt.model.Material_supplier;
import net.sf.json.JSONObject;

public class MaterialFlowingHistoryBean extends Material_flowing_history{
    private String materialIdentifier;
    private String materialName;
    private Double price;
    private String last_update_time;
    private Integer unit;
    private String unitName;
    private Integer materialSupplierSn;
    private String supplierName;
    private String buyingTimeWithUnit;
    private String updateTimeWithUnit;
    private boolean materialActive;

    public boolean isMaterialActive() {
        return materialActive;
    }

    public void setMaterialActive(boolean materialActive) {
        this.materialActive = materialActive;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getMaterialSupplierSn() {
        return materialSupplierSn;
    }

    public void setMaterialSupplierSn(Integer materialSupplierSn) {
        this.materialSupplierSn = materialSupplierSn;
    }

    public String getMaterialIdentifier() {
        return materialIdentifier;
    }

    public void setMaterialIdentifier(String materialIdentifier) {
        this.materialIdentifier = materialIdentifier;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getUpdateTimeWithUnit() {
        return updateTimeWithUnit;
    }

    public void setUpdateTimeWithUnit(String updateTimeWithUnit) {
        this.updateTimeWithUnit = updateTimeWithUnit;
    }

    public String getBuyingTimeWithUnit() {
        return buyingTimeWithUnit;
    }

    public void setBuyingTimeWithUnit(String buyingTimeWithUnit) {
        this.buyingTimeWithUnit = buyingTimeWithUnit;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
