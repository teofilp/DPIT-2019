package com.runtime_terror.myapplication.interfaces;

import android.util.Pair;

import com.runtime_terror.myapplication.models.Food;

public interface CartListener {

    void addToCart(Pair<Food, Integer> food);
    void removeFromCart(String productTitle);
}
