package com.paycoll.test.proxy;

/**
 * Created by cloud on 2017/5/13.
 */
public class RealSubject implements Subject{
    @Override
    public String getString() {
        System.out.println("no param is running");
        return "cloud";
    }

    @Override
    public String getString(String name) {
        System.out.println("param is running");
        return name;
    }
}
