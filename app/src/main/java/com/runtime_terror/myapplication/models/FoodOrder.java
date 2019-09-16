package com.runtime_terror.myapplication.models;

import java.util.List;

public class FoodOrder extends ProductOrder {
    public static final int FOOD_ORDER_TYPE = 1;
    public FoodOrder(int tableNumber, List<ProductItem> orderList) {
        super(tableNumber, orderList);
        this.orderType = FOOD_ORDER_TYPE;
    }

}
