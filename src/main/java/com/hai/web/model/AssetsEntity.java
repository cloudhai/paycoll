package com.hai.web.model;

/**
 * Created by cloud on 2017/6/6.
 */
public class AssetsEntity {

    private long uid;
    private double value;
    private String state;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
