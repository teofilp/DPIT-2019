package com.runtime_terror.myapplication.models;

import com.runtime_terror.myapplication.R;

public class BillOrder extends Order {

    public BillOrder(int tableNumber){
        super(tableNumber);
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


}
