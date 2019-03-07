package com.matt.bean;

import com.matt.model.Component;
import com.matt.model.Component_flowing_history;
import com.matt.model.Process_category;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ComponentFlowingHistoryBean extends Component_flowing_history{
    private List<ElementsOfComponentBean> materials;
    private List<ComponentBean> usedComponents;
    private Component component;
    private Process_category processCategory;
    private String timeTookInHour;
    private String updateTimeWithUnit;
    private Double materialQuantity;


    public List<ComponentBean> getUsedComponents() {
        return usedComponents;
    }

    public void setUsedComponents(List<ComponentBean> usedComponents) {
        this.usedComponents = usedComponents;
    }

    public ComponentFlowingHistoryBean(){
        this.usedComponents = new ArrayList<>();
        this.materials = new ArrayList<>();
    }

    public Double getMaterialQuantity() {
        return materialQuantity;
    }

    public void setMaterialQuantity(Double materialQuantity) {
        this.materialQuantity = materialQuantity;
    }

    public String getUpdateTimeWithUnit() {
        return updateTimeWithUnit;
    }

    public void setUpdateTimeWithUnit(String updateTimeWithUnit) {
        this.updateTimeWithUnit = updateTimeWithUnit;
    }

    public String getTimeTookInHour() {
        return timeTookInHour;
    }

    public void setTimeTookInHour(String timeTookInHour) {
        this.timeTookInHour = timeTookInHour;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public void setProcessCategory(Process_category processCategory) {
        this.processCategory = processCategory;
    }

    public List<ElementsOfComponentBean> getMaterials() {
        return materials;
    }

    public void setMaterials(List<ElementsOfComponentBean> materials) {
        this.materials = materials;
    }

    public Process_category getProcessCategory() {
        return processCategory;
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
