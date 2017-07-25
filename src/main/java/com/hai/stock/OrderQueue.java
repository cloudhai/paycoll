package com.hai.stock;

import com.sun.tools.javac.util.Assert;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by cloud on 2017/7/15.
 */
public class OrderQueue {
    private Map<Double,BlockingQueue<OrderModel>> orderMap;
    private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
    private Lock readLock = rwlock.readLock();
    private Lock writeLock = rwlock.writeLock();

    public OrderQueue(){
        orderMap = new TreeMap<>();
    }

    public OrderQueue(boolean desc){
        if(desc){
            orderMap = new TreeMap<>(new Comparator<Double>() {
                @Override
                public int compare(Double o1, Double o2) {
                    return o1==o2?0:o1>o2?-1:1;
                }
            });
        }else{
            orderMap = new TreeMap<>();
        }

    }

    public void push(OrderModel order){
        writeLock.lock();
        try{
            BlockingQueue<OrderModel> queue;
            if(isContain(order)){
                queue = orderMap.get(order.getPrice());
                queue.offer(order);
            }else{
                queue = new LinkedBlockingQueue<>();
                queue.offer(order);
                orderMap.put(order.getPrice(),queue);
            }
        }catch (Exception e){
        }finally {
            writeLock.unlock();
        }
    }

    public OrderModel poll(){
        readLock.lock();
        try{
            Iterator<Map.Entry<Double,BlockingQueue<OrderModel>>> iterator = orderMap.entrySet().iterator();
            if(iterator.hasNext()){
                BlockingQueue<OrderModel> queue = iterator.next().getValue();
                if(queue!=null && queue.size()>0){
                    return queue.poll();
                }else{
                    iterator.remove();
                    return poll();
                }
            }
            return null;
        }catch (Exception e){
            return null;
        }finally {
            readLock.unlock();
        }

    }

    private boolean isContain(OrderModel order){
        Assert.checkNonNull(order,"order can't be null");
        return orderMap.containsKey(order.getPrice());
    }

    public static void main(String[] args) {
        OrderQueue queue = new OrderQueue(true);
        Random random = new Random();
        int index = 0;
        for(int i=0;i<100;i++){
            double price = random.nextDouble()*100.0;
            queue.push(new OrderModel(price));
        }
        for(int i=0;i<100;i++){
            OrderModel order = queue.poll();
            if(order != null){
                index++;
                System.out.println(index+":   "+order.getPrice());
            }
        }
    }
}
