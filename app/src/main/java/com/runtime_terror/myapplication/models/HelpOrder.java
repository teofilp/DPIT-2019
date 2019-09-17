package com.runtime_terror.myapplication.models;

import com.runtime_terror.myapplication.R;

public class HelpOrder extends Order {
    public static final int HELP_ORDER_TYPE = 4;

    public HelpOrder() {

    }

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

    public static int getHelpOrderType() {
        return HELP_ORDER_TYPE;
    }

    @Override
    public boolean isClickable(){
        return false;
    }

    @Override
    public String getPurpose() {
        return "operations";
    }

}
