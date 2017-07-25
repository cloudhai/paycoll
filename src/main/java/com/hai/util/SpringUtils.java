package com.hai.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

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
