package com.paycoll.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by cloud on 2017/5/12.
 */
public class ThreadTest {

    public static int count = 0;

    public static void inc(Lock lock){
        try {
            Thread.sleep(1);
            lock.lock();
            count ++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
       Pool pool = new Pool();
        new PoolTakeThread(pool).start();
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new PoolPutThread(pool).start();
    }


}


class Pool{
    private int count = 0;
    private List<String> list = new ArrayList<>();
    Lock lock = new ReentrantLock();
    Condition full = lock.newCondition();
    Condition empty = lock.newCondition();
    public void put(String str){
        try {
            lock.lock();
            while (count == 5) {
                full.await();
            }
            list.add(str);
            count ++;
            empty.signal();
        }catch (Exception e) {

        }finally {
            lock.unlock();
        }
    }

    public String take(){
        try{
            lock.lock();
            while(count == 0){
                System.out.println(count);
                empty.await();
                System.out.println("go on");
            }
            System.out.println(list.size());
            String str = list.remove(count-1);
            count --;
            full.signal();
            System.out.println(str);
            return str;
        }catch (Exception e){
            return "";
        }finally {
            lock.unlock();
        }
    }
}

class PoolPutThread extends Thread{
    private Pool pool;
    public PoolPutThread(Pool pool){
        this.pool = pool;
    }
    public void run(){
        System.out.println("thread put is running");
        try {
            Thread.sleep(1000);
            pool.put("test");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PoolTakeThread extends Thread{
    private Pool pool;
    public PoolTakeThread(Pool pool){
        this.pool = pool;
    }
    public void run(){
        System.out.println("thread take is running");
        try {
            System.out.println("pool take :"+pool.take());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}