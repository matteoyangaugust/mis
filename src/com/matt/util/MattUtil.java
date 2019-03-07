package com.matt.util;

import com.matt.bean.CalendarBean;
import com.matt.bean.TimeBean;
import org.apache.commons.beanutils.BeanComparator;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class MattUtil {
    public static <T> String[] getClassFieldsArray(T ref){
        List<String> fieldNames = new ArrayList<>();
        Class c = ref.getClass();
        Field[] fields = c.getDeclaredFields();
        for(Field field : fields) {
            if(!"sn".equals(field.getName())){
                fieldNames.add(field.getName());
            }
        }
        return fieldNames.toArray(new String[fieldNames.size()]);
    }

    public static float transCm2Pound(float cm){
        return cm / 2.54F * 72F;
    }

    public static String getDateTime(){
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1 < 10 ? "0" + (cal.get(Calendar.MONTH) + 1) : cal.get(Calendar.MONTH) + 1);
        String date = String.valueOf(cal.get(Calendar.DATE) < 10 ? "0" + cal.get(Calendar.DATE) : cal.get(Calendar.DATE));
        String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + cal.get(Calendar.HOUR_OF_DAY) : cal.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(cal.get(Calendar.MINUTE) < 10 ? "0" + cal.get(Calendar.MINUTE) : cal.get(Calendar.MINUTE));
        return year + month + date + hour + minute;
    }

    public static String getDateTimeWithUnit(String dateTime){
        return "Date : " + dateTime.substring(4, 6) + "/" + dateTime.substring(6, 8) + "/" + dateTime.substring(0, 4) + "\nTime : " + dateTime.substring(8, 10) + " : " + dateTime.substring(10, 12);
//        return dateTime.substring(0, 4) + "年" + dateTime.substring(4, 6) + "月" + dateTime.substring(6, 8) + "日\n" +
//                dateTime.substring(8, 10) + "時" + dateTime.substring(10, 12) + "分";
    }

    public static String getDateTimeWithUnitBreak(String dateTime){
        return "" + dateTime.substring(4, 6) + "/" + dateTime.substring(6, 8) + "/" + dateTime.substring(0, 4) + "\n" + dateTime.substring(8, 10) + " : " + dateTime.substring(10, 12);
    }

    public static String getDateTimeWithUnitWithoutBreak(String dateTime){
        return "" + dateTime.substring(4, 6) + "/" + dateTime.substring(6, 8) + "/" + dateTime.substring(0, 4) + " " + dateTime.substring(8, 10) + " : " + dateTime.substring(10, 12);
    }

    public static String getDateWithUnit(String dateTime){
        return dateTime.substring(4, 6) + "/" + dateTime.substring(6, 8) + "/" + dateTime.substring(0, 4);
//        return dateTime.substring(0, 4) + "年" + dateTime.substring(4, 6) + "月" + dateTime.substring(6, 8) + "日";
    }

    public static String transNumberToMoneyNumber(Double number){
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(number);
    }

    /**
     * 260 = 04:20
     */
    public static String getHourMinute(Long minute){
        return LocalTime.MIN.plus(Duration.ofMinutes(minute)).toString();
    }

    /**
     *
     * @param second
     * @return 4時20分
     */
    public static String getHourMinuteWithUnit(int second){
        TimeBean timeBean = timeConvert(second);
        String time = "";
        if(timeBean.getDay() != 0){
            time = timeBean.getDay() + " Day "
                    + timeBean.getHour() + " Hour "
                    + timeBean.getMinute() + " Minute "
                    + timeBean.getSecond() + " Second ";
        }else if(timeBean.getHour() != 0){
            time = timeBean.getHour() + " Hour "
                    + timeBean.getMinute() + " Minute "
                    + timeBean.getSecond() + " Second ";
        }else if(timeBean.getMinute() != 0){
            time = timeBean.getMinute() + " Minute "
                    + timeBean.getSecond() + " Second ";
        }else if(timeBean.getSecond() != 0){
            time = timeBean.getSecond() + " Second ";
        }
        return time;
    }

    public static <T> Comparator<T> getComparator(T ref, String propertyName, boolean isReverse){
        Comparator<T> comp;
        if(isReverse){
            comp = new BeanComparator(propertyName, Collections.reverseOrder());
        }else{
            comp = new BeanComparator(propertyName);
        }
        return comp;
    }

    /**
     * 用秒數轉出實際時間單位的Bean
     * @param second
     * @return
     */
    public static TimeBean timeConvert(Integer second){
        TimeBean timeBean = new TimeBean();
        if(second == null){
            second = 0;
        }
        timeBean.setDay(second/60/60/24);
        timeBean.setHour(second/60/60%24);
        timeBean.setMinute(second/60%60);
        timeBean.setSecond(second%60);
        return timeBean;
    }

    public static CalendarBean getToday() {
        CalendarBean calendarBean = new CalendarBean();
        Calendar cal = Calendar.getInstance();
        calendarBean.setYear(cal.get(Calendar.YEAR));
        calendarBean.setMonth(cal.get(Calendar.MONTH) + 1);
        calendarBean.setDate(cal.get(Calendar.DAY_OF_MONTH));
        return calendarBean;
    }
}
