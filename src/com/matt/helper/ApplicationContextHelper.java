package com.matt.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext appCtx;
    /**
     * 此方法可以把ApplicationContext對象inject到當前類中作為一個靜態成員變量。
     * @param applicationContext ApplicationContext 對象.
     * @throws BeansException
     * @author wangdf
     */
    @Override
    public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {
        appCtx = applicationContext;
    }/**
     * 獲取ApplicationContext
     * @return
     * @author wangdf
     */
    public static ApplicationContext getApplicatsionContext(){
        return appCtx;
    }/**
     * 這是一個便利的方法，幫助我們快速得到一個BEAN
     * @param beanName bean的名字
     * @return 返回一個bean對象
     * @author wangdf
     */
    public static Object getBean( String beanName ) {
        return appCtx.getBean( beanName );
    }
}
