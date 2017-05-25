package com.paycoll.test.proxy;

import javax.management.DynamicMBean;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by cloud on 2017/5/13.
 */
public class DynamicProxy implements InvocationHandler {

    private Object object;

    public DynamicProxy(Object o){
        this.object = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String interfaces = method.getDeclaringClass().toString();
        String name = method.getName();
        Object result = method.getReturnType();
        System.out.println("method name:"+name+" resultType:"+result.toString()+"class:"+interfaces);
        return null;
    }
}
