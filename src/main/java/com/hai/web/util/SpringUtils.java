package com.hai.web.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Created by cloud on 2017/3/22.
 */
public class SpringUtils {

    private static WebApplicationContext context;
    private static WebApplicationContext getContext(){
        if(context == null){
            context = ContextLoader.getCurrentWebApplicationContext();
        }
        return context;
    }

    public static<T> T  getBean(String beanName){
        return (T) getContext().getBean(beanName);
    }

    public static<T> T getBean(Class<T> clazz){
        return getContext().getBean(clazz);
    }
}
