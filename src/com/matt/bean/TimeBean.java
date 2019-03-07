package com.matt.bean;

import net.sf.json.JSONObject;

public class TimeBean {
    private Integer day;
    private Integer hour;
    private Integer minute;
    private Integer second;

    public TimeBean(){
        this.day = 0;
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public String getTimeBeanChineseString(){
        return (day != 0 ? day + "日" : "") +
                (day == 0 && hour == 0 ? "" : hour + "時") +
                (day == 0 && hour == 0 && minute == 0 ? "" : minute + "分")
                + second + "秒";
    }

    @Override
    public String toString() {
        JSONObject jsonObj = JSONObject.fromObject(this);
        return jsonObj.toString();
    }
}
