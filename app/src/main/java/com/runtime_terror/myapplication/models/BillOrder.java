package com.runtime_terror.myapplication.models;

import com.runtime_terror.myapplication.R;

import java.util.List;

public class BillOrder extends Order {
    public static final int BILL_ORDER_TYPE = 3;

    public List<ProductItem> orderList;

    public void setOrderList(List<ProductItem> orderList) {
        this.orderList = orderList;
    }

    public BillOrder() {
        this.orderType = BILL_ORDER_TYPE;
    }

    public BillOrder(int tableNumber) {
        super(tableNumber);
        this.orderType = BILL_ORDER_TYPE;
    }


    public BillOrder(int tableNumber, List<ProductItem> orderList){
        super(tableNumber);
        this.orderList = orderList;
        this.orderType = BILL_ORDER_TYPE;
    }

    public List<ProductItem> getOrderList() {
        return orderList;
    }

    @Override
    public int getColor() {
        return R.color.lightGreen;
    }

    @Override
    public boolean isClosable() {
        return false;
    }

    @Override
    public boolean isClickable() {
        return true;
    }

    @Override
    public String getPurpose() {
        return "operations";
    }


}
