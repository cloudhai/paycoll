package com.hai.stock;

/**
 * Created by cloud on 2017/7/15.
 */
public class OrderModel {

    public OrderModel(){}
    public OrderModel(double price){this.price=price;}
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    private double price;
    private long amount;
    private long orderId;

}
