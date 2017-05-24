package com.paycoll.test;

/**
 * Created by cloud on 2017/4/28.
 */
public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new RunImpl());
        Thread t2 = new Thread(new RunImpl());
        System.out.println("thread start");
        t.start();
        t2.start();
        System.out.println("start join");
        t.join(0);
        t2.join();
        System.out.println("join finish");
    }
}

class RunImpl implements Runnable{

    @Override
    public void run() {
        System.out.println("sleep start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("wake up");
    }
}