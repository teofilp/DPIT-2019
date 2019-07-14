package com.runtime_terror.myapplication.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private List<Food> orderList;
    private Date orderDate;
    int tableNumber;

    public Order(int tableNumber) {
        orderDate = new Date();
        orderList = new ArrayList<>();
        this.tableNumber = tableNumber;
    }

    public void addFood(Food item) {
        orderList.add(item);
    }

    public int getOrderTable() {
        return this.tableNumber;
    }

    public Date getOrderDate(){
        return orderDate;
    }

    public List<Food> getOrderList() {
        return this.orderList;
    }


}
