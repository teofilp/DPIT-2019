package com.runtime_terror.myapplication.models;

import com.runtime_terror.myapplication.R;

public class HelpOrder extends Order {

    public HelpOrder(int tableNumber) {
        super(tableNumber);
    }

    @Override
    public int getColor() {
        return R.color.red;
    }

    @Override
    public boolean isClosable() {
        return true;
    }

    @Override
    public boolean isClickable(){
        return false;
    }

    @Override
    public String getPurpose() {
        return "";
    }

}
