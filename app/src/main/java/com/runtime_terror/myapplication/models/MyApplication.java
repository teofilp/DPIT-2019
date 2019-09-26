package com.runtime_terror.myapplication.models;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MyApplication extends Application {
    private List<Pair<ProductItem, Integer>> cartList = new ArrayList<>();
    private List<Pair<ProductItem, Integer>> orderList = new ArrayList<>();
    private TextView cartItems;
    private Order staffActiveOrder;

    private int clientTableNumber;
    private String clientRestaurantId;

    public int getClientTableNumber() {
        return clientTableNumber;
    }

    public void setClientTableNumber(int clientTableNumber) {
        this.clientTableNumber = clientTableNumber;
    }

    public String getClientRestaurantId() {
        return clientRestaurantId;
    }

    public void setClientRestaurantId(String clientRestaurantId) {
        this.clientRestaurantId = clientRestaurantId;
    }

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
        } else {
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

    public List<Pair<ProductItem, Integer>> getCartList(){
        return cartList;
    }

    public void placeOrder() {
        orderList.addAll(cartList);

        emptyCart();
    }

    public void requestBill() {
        orderList.clear();
    }

    public void emptyCart() {
        cartList.clear();
        updateCartItemsView();
    }


    public List<ProductItem> getOrderList() {
        List<ProductItem> finalOrderList = new ArrayList<>();

        for(Pair<ProductItem, Integer> pair: orderList){
            ProductItem item = pair.first;
            int qty = pair.second;
            item.setQty(qty);

            finalOrderList.add(item);
        }

        return finalOrderList;
    }

    public List<ProductItem> getCartListMergedWithOrderedList(){

        List<ProductItem> merged = new ArrayList<>();

        for(Pair<ProductItem, Integer> pair: orderList){
            ProductItem item = pair.first;

            int qty = pair.second;
            item.setQty(qty);

            merged.add(item);
        }

        for(Pair<ProductItem, Integer> pair: cartList){
            ProductItem item = pair.first;

            int qty = pair.second;
            item.setQty(qty);

            merged.add(item);
        }

        return merged;

    }
    public void setOrderList(List<Pair<ProductItem, Integer>> orderList) {
        this.orderList = orderList;
    }
}
