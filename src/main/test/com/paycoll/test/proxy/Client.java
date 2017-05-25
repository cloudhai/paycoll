package com.paycoll.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by cloud on 2017/5/13.
 */
public class Client {

    public static void main(String[] args) {
//        RealSubject realSubject = new RealSubject();
        InvocationHandler handler = new DynamicProxy(new Object());
        Subject subject = (Subject) Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                new Class[]{Subject.class},handler);
        System.out.println(subject.getString());
    }
}
