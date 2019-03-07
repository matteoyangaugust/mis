package com.matt.model;

public class Component_flowing_history {
    private Integer sn;
    private Integer component_sn;
    private Integer process_category_sn;
    private Integer process_amount;
    private Integer amount;
    private Integer total_duration;
    private String update_time;
    private Double purchase_fee;
    private Integer remain;

    public Integer getProcess_amount() {
        return process_amount;
    }

    public void setProcess_amount(Integer process_amount) {
        this.process_amount = process_amount;
    }

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public Integer getTotal_duration() {
        return total_duration;
    }

    public void setTotal_duration(Integer total_duration) {
        this.total_duration = total_duration;
    }

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

    public Integer getProcess_category_sn() {
        return process_category_sn;
    }

    public void setProcess_category_sn(Integer process_category_sn) {
        this.process_category_sn = process_category_sn;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public Double getPurchase_fee() {
        return purchase_fee;
    }

    public void setPurchase_fee(Double purchase_fee) {
        this.purchase_fee = purchase_fee;
    }


    @Override
    public String toString() {
        return "\nComponent_flowing_history{" +
                "sn=" + sn +
                ", component_sn=" + component_sn +
                ", process_category_sn=" + process_category_sn +
                ", amount=" + amount +
                ", total_duration=" + total_duration +
                ", update_time='" + update_time + '\'' +
                ", purchase_fee=" + purchase_fee +
                ", remain=" + remain +
                '}';
    }
}
