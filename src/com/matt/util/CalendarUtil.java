package com.matt.util;

import java.util.Calendar;

public class CalendarUtil {

    /**
     * 取得現在時間
     * @return 20170101
     */
    public static String getNowDate(){
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = cal.get(Calendar.MONTH) + 1 < 10 ?
                "0" + (cal.get(Calendar.MONTH) + 1) : String.valueOf(cal.get(Calendar.MONTH) + 1);
        String date = cal.get(Calendar.DATE) < 10 ?
                "0" + (cal.get(Calendar.DATE)) : String.valueOf(cal.get(Calendar.DATE));
        return year + month + date;
    }

    /**
     * 取得現在時間 24小時制(ex 13:30)
     * @return
     */
    public static String getNowTime(){
        Calendar cal = Calendar.getInstance();
        String hour = cal.get(Calendar.HOUR) < 10 ? "0" + cal.get(Calendar.HOUR) : String.valueOf(cal.get(Calendar.HOUR));
        String minute = cal.get(Calendar.MINUTE) < 10 ? "0" + cal.get(Calendar.MINUTE) : String.valueOf(cal.get(Calendar.MINUTE));
        return hour + ":" + minute;
    }

    /**
     * 轉換時間 0900 -> 09:00
     * @param time
     * @return
     */
    public static String formatTime(String time){
        return time.substring(0, 2) + ":" + time.substring(2);
    }
}
