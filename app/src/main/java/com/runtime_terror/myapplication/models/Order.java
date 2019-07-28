package com.runtime_terror.myapplication.models;

import java.util.Date;

public abstract class Order {
    private int tableNumber;
    private Date requestDate;

    public Order(int tableNumber){
        this.tableNumber = tableNumber;
        requestDate = new Date();
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public abstract int getColor();
    public abstract boolean isClosable();
    public abstract boolean isClickable();
}
