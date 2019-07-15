package com.runtime_terror.myapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.runtime_terror.myapplication.adapters.FoodListAdapter;


import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private RecyclerView mainList;
    private RecyclerView.Adapter adapter;
    public static final String TAG = "OrderDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        bindMainList();
        List<Food> foodList = getFoodData();
        setupAdapterAndData(foodList);
        setupToolbar();

    }

    private void bindMainList(){
        mainList = findViewById(R.id.mainOrderList);
        mainList.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Food> getFoodData(){
        List<Food> foodList = new ArrayList<>();
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        foodList.add(new Food("","Title 1",25.0, "Some reqs", 2,false));
        return foodList;
    }

    private void setupAdapterAndData(List<Food> foodList){

        adapter = new FoodListAdapter(foodList, "client", this);
        mainList.setAdapter(adapter);
    }

    private void setupToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Checkout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

}
