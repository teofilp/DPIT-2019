package com.runtime_terror.myapplication.models;

import java.util.List;

public class FoodOrder extends ProductOrder {
    public static final int FOOD_ORDER_TYPE = 1;

    public FoodOrder() {}

    public FoodOrder(int tableNumber) {
        super(tableNumber);
        this.orderType = FOOD_ORDER_TYPE;
    }

    public FoodOrder(int tableNumber, List<ProductItem> orderList) {
        super(tableNumber, orderList);
        this.orderType = FOOD_ORDER_TYPE;
    }
    public List<ProductItem> getOrderList() {
        return orderList;}}

