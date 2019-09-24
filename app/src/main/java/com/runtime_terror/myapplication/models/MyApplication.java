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
    private List<Pair<ProductItem, Integer>> cartList;
    private List<Pair<ProductItem, Integer>> orderList;
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

    public List getCartList(){
        return cartList;
    }

    public void placeOrder(Activity activity) {
        orderList = new ArrayList<>();
        orderList.addAll(cartList);
        saveOrderDetailsLocally(activity);
        updateCartItemsView();

//        emptyCart();
    }

    private void saveOrderDetailsLocally(Activity activity) {
        SharedPreferences sharedPrefs = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt("tableNumber", getClientTableNumber());
        editor.putString("restaurantId", getClientRestaurantId());
        editor.putLong("time", (new Date()).getTime());
        editor.putString("orderList", (new Gson()).toJson(orderList));
        editor.apply();

    }

    public void emptyCart() {
        cartList.clear();
        updateCartItemsView();
    }
}
