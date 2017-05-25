package com.paycoll.test;



/**
 * Created by cloud on 2017/5/10.
 */
public class WaitTest {

    public static void main(String[] args) throws InterruptedException {
        TheadTest theadTest = new TheadTest(true);
        theadTest.start();
        synchronized (theadTest){
            System.out.println("get lock");
            Thread.sleep(3000);
//            theadTest.notifyAll();
        }

        TheadTest t2 = new TheadTest(false);
        t2.start();
        Thread.sleep(2000);
        synchronized (theadTest){
            System.out.println("get other luck");
            theadTest.notify();
        }

    }


}

class TheadTest extends  Thread{
    private boolean run;
    public TheadTest(boolean run){
        this.run = run;
    }

    public void run(){
        String name = Thread.currentThread().getName();
        try {
            Thread.sleep(1000);
            System.out.println(name+":1");
            Thread.sleep(1000);
            System.out.println(name+":2");
            Thread.sleep(1000);
            System.out.println(name+":3");
            synchronized (this){
                if(run){
                    wait();
                }
            }
            Thread.sleep(1000);
            System.out.println(name+":4");
            Thread.sleep(1000);
            System.out.println(name+":5");
            Thread.sleep(1000);
            System.out.println(name+":6");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
