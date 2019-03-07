package com.matt.bean;

import com.matt.model.*;

import java.util.List;

public class ReportBean {
    private List<Material> materials;
    private List<Component> components;
    private List<Product> products;
    private List<Process_category> categories;
    private List<Material_supplier> suppliers;
    private List<Elements_of_component> elements_of_components;
    private List<Component_of_product> componentOfProducts;
    private List<Material_flowing_history> material_flowing_histories;
    private List<Component_flowing_history> component_flowing_histories;
    private List<Product_flowing_history> product_flowing_histories;

    public List<Elements_of_component> getElements_of_components() {
        return elements_of_components;
    }

    public void setElements_of_components(List<Elements_of_component> elements_of_components) {
        this.elements_of_components = elements_of_components;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Process_category> getCategories() {
        return categories;
    }

    public void setCategories(List<Process_category> categories) {
        this.categories = categories;
    }

    public List<Material_supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Material_supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public List<Component_of_product> getComponentOfProducts() {
        return componentOfProducts;
    }

    public void setComponentOfProducts(List<Component_of_product> componentOfProducts) {
        this.componentOfProducts = componentOfProducts;
    }

    public List<Material_flowing_history> getMaterial_flowing_histories() {
        return material_flowing_histories;
    }

    public void setMaterial_flowing_histories(List<Material_flowing_history> material_flowing_histories) {
        this.material_flowing_histories = material_flowing_histories;
    }

    public List<Component_flowing_history> getComponent_flowing_histories() {
        return component_flowing_histories;
    }

    public void setComponent_flowing_histories(List<Component_flowing_history> component_flowing_histories) {
        this.component_flowing_histories = component_flowing_histories;
    }

    public List<Product_flowing_history> getProduct_flowing_histories() {
        return product_flowing_histories;
    }

    public void setProduct_flowing_histories(List<Product_flowing_history> product_flowing_histories) {
        this.product_flowing_histories = product_flowing_histories;
    }

    @Override
    public String toString() {
        return "\nReportBean{" +
                "materials=" + materials +
                ", components=" + components +
                ", products=" + products +
                ", categories=" + categories +
                ", suppliers=" + suppliers +
                ", componentOfProducts=" + componentOfProducts +
                ", material_flowing_histories=" + material_flowing_histories +
                ", component_flowing_histories=" + component_flowing_histories +
                ", product_flowing_histories=" + product_flowing_histories +
                '}';
    }
}
