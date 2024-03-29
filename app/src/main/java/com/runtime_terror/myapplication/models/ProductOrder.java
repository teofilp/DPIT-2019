package com.runtime_terror.myapplication.models;

import com.runtime_terror.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ProductOrder extends Order {
    public List<ProductItem> orderList;

    public ProductOrder()
    {

    }

    public ProductOrder(int tableNumber) {
        super(tableNumber);
        orderList = new ArrayList<>();
    }

    public ProductOrder(int tableNumber, List<ProductItem> orderList) {
        super(tableNumber);
        this.orderList = orderList;
    }

    public void addFood(ProductItem item) {
        orderList.add(item);
    }

    public List<ProductItem> getOrderList() {
        return this.orderList;
    }

    public void setOrderList(List<ProductItem> orderList) {
        this.orderList = orderList;
    }

    @Override
    public int getColor() {
        int color;
        if(orderList.size() <= 2)
            color = R.color.lightGreen;
        else if (orderList.size() <= 4)
            color = R.color.darkGreen;
        else if(orderList.size() <= 6)
            color = R.color.yellow;
        else if(orderList.size() <= 10)
            color = R.color.orange;
        else
            color = R.color.red;

        return color;
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
        return "delivery";
    }
}
