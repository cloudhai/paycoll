package com.hai.web.base;

import com.hai.util.LogUtils;

import javax.servlet.ServletContextEvent;

/**
 * Created by cloud on 2017/6/6.
 */
public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        LogUtils.log.info("system: {} start finish!","paycoll");

    }
}
