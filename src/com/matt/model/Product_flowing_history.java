package com.matt.model;

import net.sf.json.JSONObject;

public class Product_flowing_history {
    public static final Integer ACTION_PRODUCE = 0;
    public static final Integer ACTION_SELL = 1;
    private Integer sn;
    private Integer product_sn;
    private Integer amount;
    private String update_time;
    private Integer remain;
    private Integer action;

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public Integer getProduct_sn() {
        return product_sn;
    }

    public void setProduct_sn(Integer product_sn) {
        this.product_sn = product_sn;
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

    public Integer getRemain() {
        return remain;
    }

    public void setRemain(Integer remain) {
        this.remain = remain;
    }

    public String getActionString(){
        if(this.getAction().equals(Product_flowing_history.ACTION_SELL)){
            return "賣出";
        }else{
            return "生產";
        }
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
