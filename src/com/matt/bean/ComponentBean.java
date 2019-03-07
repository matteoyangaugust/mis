package com.matt.bean;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ComponentBean{
    private Integer sn;
    private Integer process_category_sn;
    private Integer material_sn;
    private String identifier;
    private String name;
    private Double material_amount;
    private Integer process_duration;
    private Integer process_yield;
    private Integer remain;
    private Double purchase_fee;
    private String materialName;
    private Integer materialUnit;
    private String processCategoryName;
    private Integer active;
    private boolean materialActive;
    private boolean purchase;
    private List<ElementsOfComponentBean> materials;
    private List<ElementsOfComponentBean> components;
    private Integer componentQuantity;
    private Integer element_amount;

    public boolean isPurchase() {
        return purchase;
    }

    public void setPurchase(boolean purchase) {
        this.purchase = purchase;
    }

    public Integer getElement_amount() {
        return element_amount;
    }

    public void setElement_amount(Integer element_amount) {
        this.element_amount = element_amount;
    }

    public List<ElementsOfComponentBean> getComponents() {
        return components;
    }

    public void setComponents(List<ElementsOfComponentBean> components) {
        this.components = components;
    }

    public Integer getComponentQuantity() {
        return componentQuantity;
    }

    public void setComponentQuantity(Integer componentQuantity) {
        this.componentQuantity = componentQuantity;
    }

    public ComponentBean(){
        this.components = new ArrayList<>();
        this.materials = new ArrayList<>();
    }
    //總需時間
    private TimeBean totalTimeBean;
    //單製程時間
    private TimeBean singleTimeBean;
    //給ProductBean用的
    private Integer quantity;

    public List<ElementsOfComponentBean> getMaterials() {
        return materials;
    }

    public void setMaterials(List<ElementsOfComponentBean> materials) {
        this.materials = materials;
    }

    public Integer getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(Integer materialUnit) {
        this.materialUnit = materialUnit;
    }

    public boolean isMaterialActive() {
        return materialActive;
    }

    public void setMaterialActive(boolean materialActive) {
        this.materialActive = materialActive;
    }

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

    public TimeBean getSingleTimeBean() {
        return singleTimeBean;
    }

    public void setSingleTimeBean(TimeBean singleTimeBean) {
        this.singleTimeBean = singleTimeBean;
    }

    public TimeBean getTotalTimeBean() {
        return totalTimeBean;
    }

    public void setTotalTimeBean(TimeBean totalTimeBean) {
        this.totalTimeBean = totalTimeBean;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getSn() {
        return sn;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public Integer getMaterial_sn() {
        return material_sn;
    }

    public void setMaterial_sn(Integer material_sn) {
        this.material_sn = material_sn;
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

    public Double getMaterial_amount() {
        return material_amount;
    }

    public void setMaterial_amount(Double material_amount) {
        this.material_amount = material_amount;
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

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getProcessCategoryName() {
        return processCategoryName;
    }

    public void setProcessCategoryName(String processCategoryName) {
        this.processCategoryName = processCategoryName;
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
