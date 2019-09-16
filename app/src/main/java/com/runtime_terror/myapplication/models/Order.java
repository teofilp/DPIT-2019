package com.runtime_terror.myapplication.models;

import java.util.Date;

public abstract class Order {

    private int tableNumber;
    private Date requestDate;
    protected int orderType;
    private ORDER_STATUS status;
    private String id;

    public Order() {

    }


    public Order(int tableNumber){
        this.tableNumber = tableNumber;
        requestDate = new Date();
        status = ORDER_STATUS.PLACED;
    }

    public static enum ORDER_STATUS {
        PLACED,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(ORDER_STATUS status) {
        this.status = status;
    }

    public ORDER_STATUS getStatus() {
        return this.status;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }


    public int getTableNumber() {
        return tableNumber;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public abstract int getColor();
    public abstract boolean isClosable();
    public abstract boolean isClickable();
    public abstract String getPurpose();
}
