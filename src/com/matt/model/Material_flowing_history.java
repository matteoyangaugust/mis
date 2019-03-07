package com.matt.model;

import net.sf.json.JSONObject;

public class Material_flowing_history {
    private Integer sn;
    private Integer material_sn;
    private String buying_time;
    private String update_time;
    private Integer supplier_sn;
    private Double quantity;
    private Double remain;
    private Double fee;

    public Integer getSupplier_sn() {
        return supplier_sn;
    }

    public void setSupplier_sn(Integer supplier_sn) {
        this.supplier_sn = supplier_sn;
    }

    public Double getRemain() {
        return remain;
    }

    public void setRemain(Double remain) {
        this.remain = remain;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public Integer getMaterial_sn() {
        return material_sn;
    }

    public void setMaterial_sn(Integer material_sn) {
        this.material_sn = material_sn;
    }

    public String getBuying_time() {
        return buying_time;
    }

    public void setBuying_time(String buying_time) {
        this.buying_time = buying_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
