package com.matt.bean;

public class CalendarBean {
    private Integer year;
    private Integer month;
    private Integer date;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "\nCalendarBean{" +
                "year=" + year +
                ", month=" + month +
                ", date=" + date +
                '}';
    }
}
