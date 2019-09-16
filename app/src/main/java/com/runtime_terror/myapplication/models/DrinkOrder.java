package com.runtime_terror.myapplication.models;

import java.util.List;

public class DrinkOrder extends ProductOrder {
    public static final int DRINK_ORDER_TYPE = 2;
    public DrinkOrder(int tableNumber, List<ProductItem> orderList) {
        super(tableNumber, orderList);
        this.orderType = DRINK_ORDER_TYPE;
    }
}
