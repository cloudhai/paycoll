package com.paycoll.test;

/**
 * Created by cloud on 2017/5/25.
 */
public class DrewModel {
    private int total;
    private int value;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isFull(){
        return this.value == this.total;
    }
}
