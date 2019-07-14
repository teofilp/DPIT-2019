package com.runtime_terror.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.runtime_terror.myapplication.adapters.FoodListAdapter;
import com.runtime_terror.myapplication.models.Food;


import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private RecyclerView mainList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        bindMainList();
        setupAdapterAndData();
        setupToolbar();
    }

    private void bindMainList(){
        mainList = findViewById(R.id.mainOrderList);
        mainList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupAdapterAndData(){
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        adapter = new FoodListAdapter(foodList, "client");
        mainList.setAdapter(adapter);
    }

    private void setupToolbar(){
        setTitle("Checkout");
    }

}
