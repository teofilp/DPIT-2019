package com.runtime_terror.myapplication.models;

import android.app.Application;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private List<Pair<ProductItem, Integer>> cartList;
    private TextView cartItems;
    private Order staffActiveOrder;

    public TextView getCartItems() {
        return cartItems;
    }

    public Order getStaffActiveOrder() {
        return staffActiveOrder;
    }

    public void setStaffActiveOrder(Order staffActiveOrder) {
        this.staffActiveOrder = staffActiveOrder;
    }

    public void setCartList(List<Pair<ProductItem, Integer>> list) {
        this.cartList = list;
    }

    public void setCartItems(TextView items) {
        this.cartItems = items;
    }

    public void updateCartItemsView() {
        if(cartList.size() > 0) {
            cartItems.setVisibility(View.VISIBLE);
            cartItems.setText(Integer.toString(cartList.size()));
        }
        else {
            cartItems.setVisibility(View.GONE);
        }
    }

    public void deleteItemFromCart(String title) {
        int position = -1;

        for (int i=0; i< cartList.size(); i++){
            Pair<ProductItem, Integer> item = cartList.get(i);

            if(item.first.getTitle().equals(title)){
                position = i;
                break;
            }
        }

        if(position != -1) {
            cartList.remove(position);
            updateCartItemsView();
        }
    }

    public List getCartList(){
        return cartList;
    }
}
