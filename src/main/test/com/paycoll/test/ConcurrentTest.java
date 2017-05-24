package com.paycoll.test;

import com.hai.web.util.RandomUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by cloud on 2017/3/8.
 */
public class ConcurrentTest {


    public static void main(String[] args) {
        RandomUtils.getRandomStr(10);
    }
}



class LockSeq{
    private AtomicLong count;
    private ReentrantLock lock = new ReentrantLock();
    public void inc(){
        lock.lock();
        try{
            count.incrementAndGet();
        }catch (Exception e){

        }
        finally {
            lock.unlock();
        }
    }
    public long get(){
        return count.get();
    }
}
class Seq{
    private long count;
    public void inc(){
        count++;
    }
    public long get(){
        return count;
    }
}
