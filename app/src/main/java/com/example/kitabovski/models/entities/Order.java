package com.example.kitabovski.models.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.List;

@Entity(tableName = "orders")
public class Order {

    @PrimaryKey
    @NonNull
    private String orderId;
    private String buyerId;
    private List<String> bookIds;
    private double totalPrice;
    private long orderDate;

    public Order() {
        // Default constructor
    }

    public Order(@NonNull String orderId, String buyerId, List<String> bookIds, double totalPrice, long orderDate) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.bookIds = bookIds;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    @NonNull
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(@NonNull String orderId) {
        this.orderId = orderId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public List<String> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<String> bookIds) {
        this.bookIds = bookIds;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }
}