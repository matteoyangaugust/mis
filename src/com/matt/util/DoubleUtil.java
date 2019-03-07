package com.matt.util;

import java.math.BigDecimal;

public class DoubleUtil {

    //默認除法運算精度
    private static final int DEF_DIV_SCALE = 2;

    public static Double add(Double x, Double y){
        return new BigDecimal(x).add(new BigDecimal(y)).doubleValue();
    }

    public static Double sub(Double x, Double y){
        return new BigDecimal(x).subtract(new BigDecimal(y)).doubleValue();
    }

    public static Double mul(Double x, Double y){
        return new BigDecimal(x).multiply(new BigDecimal(y)).doubleValue();
    }

    public static Double div(double v1,double v2) {
        return div(v1,v2,DEF_DIV_SCALE);
    }

    public static Double div(double v1,double v2,int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 去小數點
     * @param a
     * @return
     */
    public static String removeUselessZero(double a){
        double b = ((double) Math.round(a*100))/100;
        String c = ""+b;
        //while ("0.".indexOf(c.substring(c.length()-1,c.length())) >=0) {
        while ("0.".indexOf(c.substring(c.length()-1,c.length())) >=0 && c.length() > 1) {
            c=c.substring(0,c.length()-1);
        }
        return c;
    }
}
