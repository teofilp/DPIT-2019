package com.runtime_terror.myapplication.interfaces;

import android.util.Pair;

import com.runtime_terror.myapplication.models.ProductItem;

public interface CartListener {

    void addToCart(Pair<ProductItem, Integer> food);
    void removeFromCart(String productTitle);
}
