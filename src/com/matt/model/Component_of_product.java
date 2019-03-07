package com.matt.model;

public class Component_of_product {
    private Integer sn;
    private Integer component_sn;
    private Integer product_sn;
    private Integer component_amount;

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public Integer getComponent_sn() {
        return component_sn;
    }

    public void setComponent_sn(Integer component_sn) {
        this.component_sn = component_sn;
    }

    public Integer getProduct_sn() {
        return product_sn;
    }

    public void setProduct_sn(Integer product_sn) {
        this.product_sn = product_sn;
    }

    public Integer getComponent_amount() {
        return component_amount;
    }

    public void setComponent_amount(Integer component_amount) {
        this.component_amount = component_amount;
    }

    @Override
    public String toString() {
        return "\nComponent_of_product{" +
                "sn=" + sn +
                ", component_sn=" + component_sn +
                ", product_sn=" + product_sn +
                ", component_amount=" + component_amount +
                '}';
    }
}
