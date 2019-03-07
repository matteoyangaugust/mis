package com.matt.bean;

import net.sf.json.JSONObject;

import java.util.Map;

public class ResultBean {
    private String msg;
    private boolean success;
    private Map<String, Object> data;

    public ResultBean(){}

    public ResultBean(boolean success, String msg){
        this.setSuccess(success);
        this.setMsg(msg);
    }

    public ResultBean(boolean success, String msg, Map<String, Object> map){
        this.setSuccess(success);
        this.setMsg(msg);
        this.setData(map);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
